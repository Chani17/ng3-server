package com.nggg.ng3.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nggg.ng3.model.GalleryDTO;
import com.nggg.ng3.model.LikeDTO;
import com.nggg.ng3.service.GalleryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GalleryController {

	private final GalleryService galleryService;

	@GetMapping("/images")
	public List<GalleryDTO> getImages(String email) {
		return galleryService.getImages(email);
	}
}
