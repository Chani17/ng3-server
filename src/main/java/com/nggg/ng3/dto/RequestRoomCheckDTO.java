package com.nggg.ng3.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestRoomCheckDTO {
    private Long roomId;
    private String password;
}