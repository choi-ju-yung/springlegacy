<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="메인화면"/>
</jsp:include>

<section id="content">
	<h2>Hello Spring</h2>
	<h3>ajax 통신하기</h3>
	<h4><button class="btn btn-outline-primary" onclick="basicAjax();">ObjectMapper로 기본 ajax처리</button></h4>
	<h4><button class="btn btn-outline-success" onclick="convertAjax();">@ResponseBody로 json 받기</button></h4>
	<h4><button class="btn btn-outline-warning" onclick="basic2();">jsp 받아오기</button></h4>
	<h4><button class="btn btn-outline-danger" onclick="allMember();">전체회원 가져오기</button></h4>
	<h4><button class="btn btn-outline-dark" onclick="insertData();">데이터 저장</button></h4>
	<div id="ajaxContainer"></div>


	<script>
		const basicAjax=()=>{
			$.get('${pageContext.request.contextPath}/ajax/basicTest.do',(data)=>{
				console.log(data);
				$("#ajaxContainer").html("<h2>"+data+"</h2>")
			});
		}
		
		const convertAjax=()=>{
			$.get("${pageContext.request.contextPath}/ajax/converter",data=>{
				console.log(data);
			});
		}
		
		const basic2=()=>{
			$.get('${pageContext.request.contextPath}/ajax/basic2',(data)=>{
				console.log(data);
				$("#ajaxContainer").html(data); // html 화면을 출력해줌
			});
		}
		
		
		const allMember=()=>{
			$.get('${pageContext.request.contextPath}/ajax/allMember',(data)=>{

				const table=$("<table>");
				const header = ["아이디","이름","나이","성별","이메일","전화번호","주소","취미","가입일"]
				
				const tr=$("<tr>");
				header.forEach(e=>{
					const th=$("<th>").text(e);
					tr.append(th);
				})
				
				table.append(tr);  // 헤더부분 붙이기
				
				data.forEach(e=>{
					const bodyTr=$("<tr>");
					const userId=$("<td>").text(e.userId);
					const name=$("<td>").text(e.userName);
					const age=$("<td>").text(e.age);
					const gender=$("<td>").text(e.gender);
					const email=$("<td>").text(e.email);
					const phone=$("<td>").text(e.phone);
					const address=$("<td>").text(e.address);
					const hobby=$("<td>").text(e.hobby.toString());
					const enrollDate=$("<td>").text(new Date(e.enrollDate));
					bodyTr.append(userId).append(name).append(age).append(gender)
					.append(email).append(phone).append(address).append(hobby).append(enrollDate);
					table.append(bodyTr);
				});
				$("#ajaxContainer").html(table);
			});
		}
		
		
		const insertData=()=>{
			const data = {userId:"test1",password:"test",userName:"테스트",age:19,gender:"M"};
			
/*  			$.ajax({
				url:"${pageContext.request.contextPath}/ajax/insertData.do",
				type:"post",
				data:JSON.stringify(data),   // 객체를 문자열 형태로 바꿔서 데이터 넘겨줌
				contentType:"application/json;charset-utf-8",
				success:data=>{
					console.log(data);
				}
			})  */
			
/* 			$.post("${pageContext.request.contextPath}/ajax/insertData.do",
					{userId:"test1",password:"test",userName:"테스트",age:19,gender:"M"},
					data=>{
						console.log(data);
					}); */
					
					
					//fetch 함수를 제공함 -> 다른 라이브러리가 필요없다
					//fetch("URL주소",{요청에 대한 옵션})
					// .then(response=>response.json()) // 응답내용을 파싱, 에러처리
					// .then(data=>{처리로직}) // success 함수
					fetch("${pageContext.request.contextPath}/ajax/allMember",{
						method:"get",
						//headers:{}
						//body:JSON.stringify(객체)
					})
					.then(response=>{
						console.log(response);
						if(!response.ok) throw new Error("요청실패!"); 
						return response.json()
						}
					)
					.then(data=>{
						console.log(data)
					}).catch(e=>{
						alert(e);
					});
		
					
/* 					fetch("${pageContext.request.contextPath}/ajax/insertData.do",{
						method:"post",
						headers:{
							"Content-type":"application/json"
						},body:JSON.stringify(data) // 자바에서 사용할수있또록 자바스크립트에서 stringfy를이용해 객체를 문자열로 변환
					}).then(response=>{
							if(!response.ok) new Error("입력실패"); 
							return response.json()}) // 서버가 json으로 응답했을 때
							// 일반문자를 반환했을때는 -> response.text()
					.then(data=>{
						console.log(data);
						}).catch(e=>{
							
						}); */
		}
		
		
		
		
		
		
	</script>
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
