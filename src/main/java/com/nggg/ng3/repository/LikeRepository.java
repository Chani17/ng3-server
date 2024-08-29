package com.nggg.ng3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nggg.ng3.entity.Like;
import com.nggg.ng3.entity.LikeId;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
	Long countByImageId(Long imageId);
}
