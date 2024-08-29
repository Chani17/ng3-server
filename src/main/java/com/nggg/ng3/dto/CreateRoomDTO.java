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