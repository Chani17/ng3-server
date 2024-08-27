package com.nggg.ng3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nggg.ng3.entity.Image;
import com.nggg.ng3.model.GalleryDTO;

public interface ImageRepository extends JpaRepository<Image, Long> {
	@Query("SELECT new com.nggg.ng3.model.GalleryDTO(I.id, I.title, I.url, COUNT(L.id)) " +
			"FROM Image I " +
			"LEFT JOIN Like L ON I.id = L.image.id " +
			"WHERE I.user.email = :userEmail " +
			"GROUP BY I.id, I.title, I.url " +
			"ORDER BY COUNT(L.id) DESC, I.title ASC")
	List<GalleryDTO> findImageLikesByUserId(@Param("userEmail") String userEmail);

}
