package com.nggg.ng3.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nggg.ng3.dto.Chat;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

	// ObjectMapper는 JSON 데이터를 객체로 변환하거나 객체를 JSON으로 변환하는 데 사용
	private final ObjectMapper objectMapper;

	// 방의 ID에 따라 WebSocket Session을 저장하는 ConcurrentHashMap을 정의
	private final Map<Long, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

	// Client가 WebSocket 연결을 열었을 때 호출
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// CHECK❗️
		// Client 요청 URI에서 쿼리 문자열을 가져옴
		String query = session.getUri().getQuery();

		// Query 문자열에서 roomId를 추출
		String roomIdStr = query != null && query.startsWith("roomId=") ? query.split("=")[1] : null;
		System.out.println("Room ID: " + roomIdStr);

		if (roomIdStr != null) {
			// 문자열로부터 long 타입의 방 ID를 parsing
			long roomId = Long.parseLong(roomIdStr);

			// CHECK❗️
			// WebSocket session의 속성에 방 번호를 추가
			session.getAttributes().put("roomId", roomIdStr);
			// 방 ID에 대한 session 집합을 가져오거나 새로 생성한 후, 현재 session을 추가
			rooms.computeIfAbsent(roomId, k -> Collections.newSetFromMap(new ConcurrentHashMap<>())).add(session);
			notifyRoomAboutUserChange(roomId, "joined", "System");
		}
	}

	// Client로부터 텍스트 메세지를 수신했을 때 호출
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		// 현재 session의 방 번호를 가져옴
		String roomIdStr = (String) session.getAttributes().get("roomId");

		if (roomIdStr != null) {
			long roomId = Long.parseLong(roomIdStr);
			System.out.println("Received message: " + message.getPayload());

			// CHECK❗️
			try {
				// 수신한 메세지를 JSON으로 파싱하여 Chat 객체로 변환
				Chat chat = objectMapper.readValue(message.getPayload(), Chat.class);
				// 메세지를 방에 있는 모든 session으로 전송
				sendMessageToRoom(roomId, chat);
			} catch (JsonParseException e) {
				System.err.println("JSON 파싱 오류: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// 방에 있는 모든 session으로 메세지를 전송
	private void sendMessageToRoom(long roomId, Chat chat) throws IOException {
		// 메세지에 현재 시간 추가
		chat.setTimestamp(LocalDateTime.now());
		// 방 ID에 해당하는 session 집합을 가져옴
		Set<WebSocketSession> roomSessions = rooms.get(roomId);
		if (roomSessions != null) {
			// 방에 있는 모든 session에 메세지 전송
			for (WebSocketSession session : roomSessions) {
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chat)));
			}
		}
	}

	// Client의 WebSocket 연결이 종료되었을 때 호출
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 현재 session의 방 ID를 가져옴
		String roomIdStr = (String) session.getAttributes().get("roomId");

		if (roomIdStr != null) {
			long roomId = Long.parseLong(roomIdStr);
			// 방 ID에 해당하는 session 집합을 가져온다.
			Set<WebSocketSession> roomSessions = rooms.get(roomId);

			if (roomSessions != null) {
				// 현재 session을 방의 session 집합에서 제거
				roomSessions.remove(session);
				// 방의 session 집합이 비어있으면 방 삭제
				if (roomSessions.isEmpty()) {
					rooms.remove(roomId);
				} else {
					notifyRoomAboutUserChange(roomId, "left", "System");
				}
			}
		}
	}

	// Notify room members about user joining or leaving
	private void notifyRoomAboutUserChange(long roomId, String action, String systemSender) throws IOException {
		Chat chat = new Chat();
		chat.setSender(systemSender);
		chat.setMessage("A user has " + action + " the room.");
		chat.setTimestamp(LocalDateTime.now());

		sendMessageToRoom(roomId, chat);
	}
}
