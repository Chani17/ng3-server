package com.nggg.ng3.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * [김찬희] : 게임 방에서 그림을 그릴 수 있는 캔버스를 웹소켓을 이용하여 구현
 */
@Component
public class CanvasHandler extends TextWebSocketHandler {

	private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 모든 세션에 메시지 전송
		for(WebSocketSession webSocketSession : sessions) {
			if(webSocketSession.isOpen()) {
				webSocketSession.sendMessage(message);
			}
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}
}
