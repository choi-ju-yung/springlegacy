package com.bs.spring.board.model.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.bs.spring.member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Board {
	
	private int boardNo;
	private String boardTitle;
	/* private String boardWriter; */
	private Member boardWriter;
	private String boardContent;
	private Date boardDate;
	private int boardReadCount;
	
	
	private List<Attachment> file = new ArrayList(); // AttachMent 객체는 Board는 종속관계이기 때문에 Board에서 필드로 저장가능
	// 1:n 관계
}
