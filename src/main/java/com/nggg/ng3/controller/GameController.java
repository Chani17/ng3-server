package com.nggg.ng3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nggg.ng3.model.SaveImageDTO;
import com.nggg.ng3.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameController {

	private final GameService gameService;

	@PostMapping("/image")
	public ResponseEntity saveImage(SaveImageDTO saveImageDTO) {
		gameService.saveImage(saveImageDTO);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
