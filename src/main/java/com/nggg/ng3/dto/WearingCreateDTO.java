package com.nggg.ng3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * [박혁진] : Wearing  관련 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WearingCreateDTO {
    private String userId;
    private AvatarComponentDTO avatarComponent;
}