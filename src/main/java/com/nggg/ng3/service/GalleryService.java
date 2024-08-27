package com.nggg.ng3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nggg.ng3.entity.Image;
import com.nggg.ng3.entity.User;
import com.nggg.ng3.model.GalleryDTO;
import com.nggg.ng3.repository.ImageRepository;
import com.nggg.ng3.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GalleryService {

	private final ImageRepository imageRepository;
	private final UserRepository userRepository;
	private final LikeRepository likeRepository;

	@Transactional(readOnly = true)
	public List<GalleryDTO> getImages(String email) {
		User user = userRepository.findById(email)
			.orElseThrow(() -> new IllegalStateException("회원정보를 확인해주세요."));

		List<GalleryDTO> imageList = imageRepository.findImageLikesByUserId(user.getEmail());

		return imageList;
	}

