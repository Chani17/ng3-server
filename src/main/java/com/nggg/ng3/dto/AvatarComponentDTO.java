package com.nggg.ng3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * [박혁진] : 아바타 컴포넌트 관련 DTO
 */
@Data
@AllArgsConstructor
public class AvatarComponentDTO {
    private Long id;
    private String imageUrl;
    private Long avatarComponentCategoryId;
}