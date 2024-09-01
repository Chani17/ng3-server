// 이하린 : 방 입장 검증 후 응답 데이터 전송 객체

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
public class ResponseRoomCheckDTO {
    private Long roomId;
    private String title;
    private List<RoomListDTO.UserDTO> users;
    private String responseMessage;

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