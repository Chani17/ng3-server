package com.nggg.ng3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nggg.ng3.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
