package com.nggg.ng3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nggg.ng3.entity.Image;
import com.nggg.ng3.entity.Like;
import com.nggg.ng3.entity.LikeId;
import com.nggg.ng3.entity.User;
import com.nggg.ng3.model.GalleryDTO;
import com.nggg.ng3.repository.ImageRepository;
import com.nggg.ng3.repository.LikeRepository;
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
		for(GalleryDTO dto : imageList) {
			System.out.println("title = " + dto.getTitle());
			System.out.println("likeCount = " + dto.getLikeCount());
			System.out.println("url = " + dto.getUrl());
		}

		return imageList;
	}

	public Long likes(String email, Long imageId) {
		User user = userRepository.findById(email)
			.orElseThrow(() -> new IllegalStateException("회원정보를 확인해주세요."));

		Image image = imageRepository.findById(imageId)
			.orElseThrow(() -> new IllegalStateException("존재하지 않는 이미지입니다."));

		LikeId likeId = new LikeId(user.getEmail(), image.getId());

		if(!isLikedByUser(email, imageId)) {
			Like like = Like.builder()
					.id(likeId)
					.user(user)
					.image(image)
					.build();

			likeRepository.save(like);
		}
		return getLikeCount(imageId);
	}

	public Long notlikes(String email, Long imageId) {
		LikeId likeId = new LikeId(email, imageId);
		if(isLikedByUser(email, imageId)) {
			likeRepository.deleteById(likeId);
		}
		return getLikeCount(imageId);
	}

	public Long getLikeCount(Long imageId) {
		return likeRepository.countByImageId(imageId);
	}

	public boolean isLikedByUser(String email, Long imageId) {
		User user = userRepository.findById(email)
				.orElseThrow(() -> new IllegalStateException("회원정보를 확인해주세요."));

		Image image = imageRepository.findById(imageId)
				.orElseThrow(() -> new IllegalStateException("존재하지 않는 이미지입니다."));
		return likeRepository.existsByUserAndImage(user, image);
	}
}
