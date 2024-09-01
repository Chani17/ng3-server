// 이하린 : 방 입장 검증시 요청받은 데이터 전송 객체

package com.nggg.ng3.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestRoomCheckDTO {
    private Long roomId;
    private String userId;
    private String password;
}
