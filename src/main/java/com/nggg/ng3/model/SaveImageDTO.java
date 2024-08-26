package com.nggg.ng3.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveImageDTO {

	private String email;

	private String word;

	private MultipartFile image;
}
