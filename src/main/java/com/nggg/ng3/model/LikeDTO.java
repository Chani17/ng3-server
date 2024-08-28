package com.nggg.ng3.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeDTO {

	private String email;

	private Long imageId;
}
