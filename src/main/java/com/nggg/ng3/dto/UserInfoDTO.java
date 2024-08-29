package com.nggg.ng3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String email;
    private String profile_image;
    private String nickname;
    private Long totalLikes;
}
