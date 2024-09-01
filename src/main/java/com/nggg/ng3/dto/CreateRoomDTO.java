// 이하린 : 새로운 채팅방 생성 데이터 전송 객체

package com.nggg.ng3.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRoomDTO {
    private String userId;
    private String title;
    private String password;
}