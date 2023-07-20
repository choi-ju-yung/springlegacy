package com.bs.spring.common.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChattingMessage {
	private String type;
	private String sender;
	private String reciever;
	private String msg;
	private String room;
}
