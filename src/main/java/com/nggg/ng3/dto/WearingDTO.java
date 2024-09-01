package com.nggg.ng3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * [박혁진] : Wearing  관련 DTO
 */
@Data
@AllArgsConstructor
public class WearingDTO {
    private Long avatarComponentId;
    private String imageUrl;
    private Long avatarComponentCategoryId;
}
