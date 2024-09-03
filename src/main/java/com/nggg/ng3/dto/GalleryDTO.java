package com.nggg.ng3.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * [김찬희] : 이미지의 ID, 제목, URL, 좋아요 수를 담는 DTO 클래스
 */
@Getter
public class GalleryDTO {

	private Long id;

	private String title;

	private String url;

	private Long likeCount;

	@Builder
	public GalleryDTO(Long id, String title, String url, Long likeCount) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.likeCount = likeCount;
	}
}
