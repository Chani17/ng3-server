package com.nggg.ng3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String email;
    private String profile_image;
    private String nickname;
    private Long totalLikes;
}
