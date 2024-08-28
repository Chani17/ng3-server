package com.nggg.ng3.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class CreateRoomDTO {
    private String userId;
    private String title;
    private String password;
}
