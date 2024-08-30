package com.nggg.ng3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WearingDTO {
    private Long avatarComponentId;
    private String imageUrl;
    private Long avatarComponentCategoryId;
}
