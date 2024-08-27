package com.nggg.ng3.controller;

import com.nggg.ng3.dto.RoomDTO;
import com.nggg.ng3.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 이 컨트롤러에 대해 CORS 허용
public class RoomController {

    private final RoomService roomService;

    // 방 목록 가져오기
    @GetMapping("/room")
    public List<RoomDTO> getRooms() {
        return roomService.getAllRooms();
    }


}