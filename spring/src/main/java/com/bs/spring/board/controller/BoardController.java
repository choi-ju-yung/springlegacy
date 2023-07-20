package com.bs.spring.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bs.spring.board.model.dto.Attachment;
import com.bs.spring.board.model.dto.Board;
import com.bs.spring.board.service.BoardService;
import com.bs.spring.common.PageFactory;
import com.bs.spring.member.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j  // 로그를 찍기위해서 어노테이션 설정
public class BoardController {
	
	private BoardService service;
	
	public BoardController(BoardService service) {
		this.service = service;
	}
	
	
	@RequestMapping("/board/selectBoardAll.do")
	public String boardList(@RequestParam(value="cPage",defaultValue = "1") int cPage
			, @RequestParam(value="numPerpage",defaultValue = "5") int numPerPage, Model m) {
		// 페이지에 맞는 데이터를 가져와야함
		
		
		List<Board> boards = service.selectBoardList(Map.of("cPage",cPage,"numPerpage",numPerPage));
		int totalData = service.selectBoardCount();
		
		// 페이징처리
		
		m.addAttribute("pageBar",PageFactory.getPage(cPage, numPerPage, totalData,"selectBoardAll.do"));
		m.addAttribute("boards",boards);
		
		return "board/boardList";
	}
	
	
	@RequestMapping("/board/detailBoard.do")
	public String boardDetail(Model m,int boardNo) {
	
		Board board = service.selectBoardDetail(boardNo);
		m.addAttribute("board",board);
		
		return "board/boardDetail";			
	}
	
	
	@RequestMapping("/board/insertBoardView.do")
	public String insertBoardView() {
		return "board/insertBoardView";
	}
	
	
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(String boardTitle,Board b, MultipartFile[] upFile, HttpSession session, Model m) { 
		// MultipartFile[] upFile (다수개 받을 수 있음)
		log.info("{}",b);
		log.info("{}",upFile);
		
	
		b.setBoardWriter((Member)session.getAttribute("loginMember"));
		
		// MultipartFile에서 제공하는 메소드를 이용해서
		// 파일을 저장할 수 있음 -> transferTo() 메소드를 이용
		// 절대경로 가져오기
		String path = session.getServletContext().getRealPath("/resources/upload/board/");
		
		// 파일명에 대한 renamed 규칙을 설정
		// 직접 리네임 규칙을 만들어서 저장해보자
		// yyyyMMdd_HHmmssSSS_랜덤값 
		/* List<Attachment> files= new ArrayList(); */
		
		if(upFile!=null) {
			for(MultipartFile mf : upFile) {
				if(!mf.isEmpty()) {
					String oriName = mf.getOriginalFilename();
					String ext = oriName.substring(oriName.lastIndexOf(".")); // 원본주소에서 확장자 빼는 작업
					
					Date today=new Date(System.currentTimeMillis()); // 현재날짜
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS"); // 날짜형식 포맷팅   
					int rdn=(int)Math.random()*10000+1; // 랜덤값
					String rename=sdf.format(today)+"_"+rdn+ext;  // 현재날짜+ _ + 랜덤값 + 확장자 로 새로운 파일명정함
								
					try {
						mf.transferTo(new File(path+rename));
					}catch (IOException e) {
						e.printStackTrace();
					}
					
					Attachment file = Attachment.builder()
										.originalFilename(oriName)
										.renamedFilename(rename)
										.build();
					
					b.getFile().add(file); // board 안에다 넣음
				}
			}
		}
		
		try {
			service.insertBoard(b);
		}catch (RuntimeException e) {
			
			for(Attachment a : b.getFile()) {  // 예외발생했을 경우, 업로드 파일안에 업로드된 파일 삭제
				File delFile = new File(path+a.getRenamedFilename());
				delFile.delete();
			}
			
			m.addAttribute("msg","글쓰기 등록실패! :p ");
			m.addAttribute("loc","/board/insertBoardView.do");
			return "common/msg";
		}	
		return "redirect:/board/selectBoardAll.do";
	}
	
	// 게시글 안의 파일 다운로드하는 컨트롤러
	@RequestMapping("/board/filedownload")
	public void fileDown(String oriname, String rename, OutputStream out,
				@RequestHeader(value="user-agent") String header, HttpSession session, HttpServletResponse res) {
		
		String path = session.getServletContext().getRealPath("/resources/upload/board/");
		File downloadFile = new File(path+rename);
		
		try(FileInputStream fis = new FileInputStream(downloadFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedOutputStream bos = new BufferedOutputStream(out)){
			
			boolean isMS = header.contains("Trident") || header.contains("MSIE");
				String ecodeRename="";
			if(isMS) { // 익스플로러 마다 한글 인식하기위해서
				ecodeRename = URLEncoder.encode(oriname,"UTF-8");
				ecodeRename = ecodeRename.replaceAll("\\+", "%20");
			}else {
				ecodeRename = new String(oriname.getBytes("UTF-8"),"ISO-8859-1");
			}
			
			res.setContentType("application/octet-stream;charset=utf-8");
			res.setHeader("Content-Disposition", "attachment;filename=\""+ecodeRename+"\"");
			
			int read=-1;
			while((read=bis.read())!=-1) {
				bos.write(read);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	
}
