package com.nggg.ng3.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nggg.ng3.model.GalleryDTO;
import com.nggg.ng3.model.LikeDTO;
import com.nggg.ng3.service.GalleryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GalleryController {

	private final GalleryService galleryService;

	@GetMapping("/{userId}/images")
	public List<GalleryDTO> getImages(@PathVariable("userId") String userId) {
		return galleryService.getImages(userId);
	}

	@PostMapping("/{imageId}/likes")
	public Long likes(@PathVariable("imageId") Long imageId, @RequestParam String userId) {
		System.out.println("userId = " + userId);
		Long likeCount = galleryService.likes(userId, imageId);
		System.out.println("likeCount = " + likeCount);
		return likeCount;
	}
}
