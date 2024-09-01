package com.nggg.ng3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * [김찬희] : 채팅 관련 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
	private String sender;
	private String message;
	private LocalDateTime timestamp;
}