package com.nggg.ng3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvatarComponentDTO {
    private Long id;
    private String imageUrl;
    private Long avatarComponentCategoryId;
}