package com.nggg.ng3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

	private final CanvasHandler canvasHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// setAllowedOrigins("*")" : 웹 소켓 cors 정책으로 인해 허용 도메인을 지정해줘야 함. 테스트이기 때문에 와일드카드(*)로 모든 도메인을 열어줌.
		registry.addHandler(canvasHandler, "/canvas").setAllowedOrigins("*");
	}
}
