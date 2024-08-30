package com.nggg.ng3.controller;

import com.nggg.ng3.dto.CreateRoomDTO;
import com.nggg.ng3.dto.RequestRoomCheckDTO;
import com.nggg.ng3.dto.ResponseRoomCheckDTO;
import com.nggg.ng3.dto.RoomListDTO;
import com.nggg.ng3.entity.Room;
import com.nggg.ng3.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://nggg.com:3000") // 이 컨트롤러에 대해 CORS 허용
public class RoomController {

    private final RoomService roomService;

    // 방 목록 가져오기
    @GetMapping("/room")
    public List<RoomListDTO> getRooms() {
        return roomService.getAllRooms();
    }

    // 방 생성하기
    @PostMapping("/room")
    public ResponseEntity<Map<String, Long>> createRoom(@RequestBody CreateRoomDTO createRoomDTO) {
        Room createdRoom = roomService.saveRoom(createRoomDTO);

        // 방 ID를 반환할 데이터 준비
        Map<String, Long> response = new HashMap<>();
        response.put("roomId", createdRoom.getId());

        return ResponseEntity.ok(response);
    }

    // 방 입장 검증
    @PostMapping("/room/enter")
    public ResponseEntity<Object> enterRoomCheck(@RequestBody RequestRoomCheckDTO requestRoomCheckDTO) {
        ResponseRoomCheckDTO responseRoomCheckDTO = roomService.enterRoomCheck(requestRoomCheckDTO);
        return ResponseEntity.ok(responseRoomCheckDTO);
    }
}