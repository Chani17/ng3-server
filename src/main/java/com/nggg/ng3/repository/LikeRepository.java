package com.nggg.ng3.repository;

import com.nggg.ng3.entity.Image;
import com.nggg.ng3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nggg.ng3.entity.Like;
import com.nggg.ng3.entity.LikeId;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
	boolean existsByUserAndImage(User user, Image image);
	Long countByImageId(Long imageId);

  @Query("SELECT COUNT(1) FROM Like l WHERE l.user.email = :userEmail")
  Long countLikeByUserEmail(@Param("userEmail") String userEmail);

}
