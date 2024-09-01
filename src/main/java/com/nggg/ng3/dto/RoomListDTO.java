// 이하린 : 방 목록 응답 데이터 전송 객체

package com.nggg.ng3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomListDTO {
    private Long id;
    private String title;
    private String password;
    private String state;
    private List<UserDTO> users;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO {
        private String email;
        private String nickname;
        private String profile_image;
    }
}