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

	private final ObjectMapper objectMapper;
	private final Map<Long, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String query = session.getUri().getQuery();
		String roomIdStr = query != null && query.startsWith("roomId=") ? query.split("&")[0].split("=")[1] : null;

		System.out.println("Room ID: " + roomIdStr);

		if (roomIdStr != null) {
			long roomId = Long.parseLong(roomIdStr);
			session.getAttributes().put("roomId", roomIdStr);

			rooms.computeIfAbsent(roomId, k -> Collections.newSetFromMap(new ConcurrentHashMap<>())).add(session);
		}
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		String roomIdStr = (String) session.getAttributes().get("roomId");

		if (roomIdStr != null) {
			long roomId = Long.parseLong(roomIdStr);
			System.out.println("Received message: " + message.getPayload());

			try {
				Chat chat = objectMapper.readValue(message.getPayload(), Chat.class);
				sendMessageToRoom(roomId, chat);
			} catch (JsonParseException e) {
				System.err.println("JSON 파싱 오류: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void sendMessageToRoom(long roomId, Chat chat) throws IOException {
		chat.setTimestamp(LocalDateTime.now());
		Set<WebSocketSession> roomSessions = rooms.get(roomId);
		if (roomSessions != null) {
			for (WebSocketSession session : roomSessions) {
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chat)));
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String roomIdStr = (String) session.getAttributes().get("roomId");

		if (roomIdStr != null) {
			long roomId = Long.parseLong(roomIdStr);
			Set<WebSocketSession> roomSessions = rooms.get(roomId);

			if (roomSessions != null) {
				roomSessions.remove(session);
				if (roomSessions.isEmpty()) {
					rooms.remove(roomId);
				}
			}
		}
	}
}
