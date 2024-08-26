package com.nggg.ng3.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadImageDTO {

	private String title;

	private String imageUrl;
}
