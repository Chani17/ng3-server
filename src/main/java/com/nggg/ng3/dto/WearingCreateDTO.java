package com.nggg.ng3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WearingCreateDTO {
    private String userId;
    private AvatarComponentDTO avatarComponent;
}