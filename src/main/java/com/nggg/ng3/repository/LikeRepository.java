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

    // 한수민: 유저 엔티티의 이메일 필드를 기준으로 받은 좋아요 총 개수 계산
    @Query("SELECT COUNT(1) FROM Like l WHERE l.user.email = :userEmail")
    Long countLikeByUserEmail(@Param("userEmail") String userEmail);

}
