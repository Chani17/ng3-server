package com.nggg.ng3.service;

import com.nggg.ng3.dto.RoomDTO;
import com.nggg.ng3.entity.Room;
import com.nggg.ng3.repository.InRoomUserRepository;
import com.nggg.ng3.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final InRoomUserRepository inRoomUserRepository;

    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .map(room -> {
                    // 방에 포함된 사용자 정보 가져오기
                    List<RoomDTO.UserDTO> users = inRoomUserRepository.findByRoomId(room.getId()).stream()
                            .map(inRoomUser -> RoomDTO.UserDTO.builder()
                                    .nickname(inRoomUser.getUser().getNickname())
                                    .build())
                            .collect(Collectors.toList());

                    return RoomDTO.builder()
                            .id(room.getId())
                            .title(room.getTitle())
                            .password(room.getPassword())
                            .state(room.getState().name())
                            .users(users)
                            .build();
                })
                .collect(Collectors.toList());
    }
}