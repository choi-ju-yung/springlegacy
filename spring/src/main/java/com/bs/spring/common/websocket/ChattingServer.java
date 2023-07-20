package com.bs.spring.common.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChattingServer extends TextWebSocketHandler{
	
	// key -> userId,  session은 전달된 값을 저장
	private Map<String,WebSocketSession> clients = new HashMap(); // 세션을 사용자가 직접만들어서 저장해야함
	private ObjectMapper mapper;
	
	public ChattingServer(ObjectMapper mapper) {
		this.mapper=mapper;
	}
	
	// alt + shift + s + v 로 구현체 만듬
	
	@Override  // onopen
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("채팅서버 입장");
		log.info(session.getId()+session.getRemoteAddress());
	
	}

	@Override //onclose
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		log.info("{}",message);
		log.info(message.getPayload()); // 클라이언트가 보낸 데이터
		ChattingMessage msg = mapper.readValue(message.getPayload(), ChattingMessage.class);
		System.out.println(msg);
		
		switch(msg.getType()) {
			case "open" : addClient(session,msg.getSender()); break;
			case "msg" : sendMessage(msg); break;
		}
	}
	
	private void addClient(WebSocketSession session, String sender) {
		clients.put(sender, session);
		log.info("현재접속자 : "+clients.size());
		String attence = clients.keySet().stream().collect(Collectors.joining(","));
		systemMessage(attence);
	}
	
	
	private void systemMessage(String msg) {
		try {
		Set<Map.Entry<String,WebSocketSession>> clients = this.clients.entrySet();
			for(Map.Entry<String,WebSocketSession> client:clients) {				
				client.getValue().sendMessage(
						new TextMessage(mapper.writeValueAsString(ChattingMessage.builder().type("system").msg(msg).build())));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void sendMessage(ChattingMessage msg) {
		Set<Map.Entry<String,WebSocketSession>> clients = this.clients.entrySet();
		try {
			if(msg.getReciever().isBlank()) {
				// 전체전송
				for(Map.Entry<String,WebSocketSession> client:clients) {
					String userId = client.getKey();
					client.getValue().sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
				}
			}else{
				for(Map.Entry<String,WebSocketSession> client:clients) {
					String userId = client.getKey();
					if(userId.equals(msg.getReciever()) || userId.equals(msg.getSender())) {
						client.getValue().sendMessage(new TextMessage(mapper.writeValueAsString(msg)));
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override // onMessage
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

	}
	
}
