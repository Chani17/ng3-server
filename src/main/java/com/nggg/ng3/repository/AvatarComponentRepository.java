package com.nggg.ng3.repository;

import com.nggg.ng3.entity.AvatarComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvatarComponentRepository extends JpaRepository<AvatarComponent, Long> {
    // 모든 AvatarComponent의 id, imageUrl, avatarComponentCategory를 가져오는 메소드
    List<AvatarComponent> findAll();
}