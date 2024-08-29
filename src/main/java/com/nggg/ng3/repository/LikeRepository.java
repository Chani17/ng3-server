package com.nggg.ng3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nggg.ng3.entity.Like;
import com.nggg.ng3.entity.LikeId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
    @Query("SELECT COUNT(1) FROM Like l WHERE l.user.email = :userEmail")
    Long countLikeByUserEmail(@Param("userEmail") String userEmail);
}
