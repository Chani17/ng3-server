package com.nggg.ng3.repository;

import com.nggg.ng3.entity.Image;
import com.nggg.ng3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nggg.ng3.entity.Like;
import com.nggg.ng3.entity.LikeId;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
	boolean existsByUserAndImage(User user, Image image);
	Long countByImageId(Long imageId);
}
