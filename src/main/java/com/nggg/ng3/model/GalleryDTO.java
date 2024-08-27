package com.nggg.ng3.model;

import lombok.Builder;
import lombok.Getter;

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
