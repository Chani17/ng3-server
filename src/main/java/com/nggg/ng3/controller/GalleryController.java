package com.nggg.ng3.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nggg.ng3.dto.GalleryDTO;
import com.nggg.ng3.service.GalleryService;

import lombok.RequiredArgsConstructor;

/**
 * [김찬희] : 데이터베이스에 저장된 이미지들을 불러오는 endpoint 작성
 * [한수민] : 좋아요 관련 endpoint 작성
 */

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class GalleryController {

	private final GalleryService galleryService;

	@GetMapping("/{userId}/images")
	public List<GalleryDTO> getImages(@PathVariable("userId") String userId) {
		return galleryService.getImages(userId);
	}

	@PostMapping("/{imageId}/likes")
	public Long addLike(@PathVariable("imageId") Long imageId, @RequestParam String userId) {
		return galleryService.likes(userId, imageId);
	}

	@DeleteMapping("/{imageId}/likes")
	public Long removeLike(@PathVariable("imageId") Long imageId, @RequestParam String userId) {
		return galleryService.notlikes(userId, imageId);
	}

	@GetMapping("/{userId}/images/{imageId}/liked")
	public boolean isLiked(@PathVariable("userId") String userId, @PathVariable("imageId") Long imageId) {
		return galleryService.isLikedByUser(userId, imageId);
	}
}
