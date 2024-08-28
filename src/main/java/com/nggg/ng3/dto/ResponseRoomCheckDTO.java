package com.nggg.ng3.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRoomCheckDTO {
    private Long roomId;
    private String title;
    private List<RoomListDTO.UserDTO> users;
    private String responseMessage;

    @Getter
//    @Builder
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO {
        private String nickname;
    }
}