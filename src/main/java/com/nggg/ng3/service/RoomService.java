package com.nggg.ng3.service;

import com.nggg.ng3.dto.CreateRoomDTO;
import com.nggg.ng3.dto.RequestRoomCheckDTO;
import com.nggg.ng3.dto.ResponseRoomCheckDTO;
import com.nggg.ng3.dto.RoomListDTO;
import com.nggg.ng3.entity.GameState;
import com.nggg.ng3.entity.InRoomUser;
import com.nggg.ng3.entity.InRoomUserId;
import com.nggg.ng3.entity.Role;
import com.nggg.ng3.entity.Room;
import com.nggg.ng3.entity.User;
import com.nggg.ng3.repository.InRoomUserRepository;
import com.nggg.ng3.repository.RoomRepository;
import com.nggg.ng3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nggg.ng3.exception.CustomExceptions.*;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final InRoomUserRepository inRoomUserRepository;
    private final UserRepository userRepository;

    // 방 목록 가져오기
    public List<RoomListDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .map(room -> {
                    // 방에 포함된 사용자 정보 가져오기
                    List<RoomListDTO.UserDTO> users = inRoomUserRepository.findByRoomId(room.getId()).stream()
                            .map(inRoomUser -> RoomListDTO.UserDTO.builder()
                                    .nickname(inRoomUser.getUser().getNickname())
                                    .build())
                            .collect(Collectors.toList());

                    return RoomListDTO.builder()
                            .id(room.getId())
                            .title(room.getTitle())
                            .password(room.getPassword())
                            .state(room.getState().name())
                            .users(users)
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 방 생성
    @Transactional
    public Room saveRoom(CreateRoomDTO createRoomDTO) {

        String currentUserEmail = createRoomDTO.getUserId();

        // DTO에서 Room 엔티티로 변환
        Room room = Room.builder()
                .title(createRoomDTO.getTitle())
                .password(createRoomDTO.getPassword())
                .state(GameState.READY) // 기본 상태를 설정
                .build();

        // Room 엔티티 저장
        Room savedRoom = roomRepository.save(room);

        // 현재 로그인된 유저 정보 가져오기
        User currentUser = userRepository.findById(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // InRoomUser 엔티티 생성 및 유저 정보 저장
        InRoomUser inRoomUser = InRoomUser.builder()
                .id(new InRoomUserId(currentUser.getEmail(), savedRoom.getId())) // InRoomUserId 설정
                .room(savedRoom)
                .user(currentUser)
                .role(Role.MASTER)
                .build();

        inRoomUserRepository.save(inRoomUser);

        // 생성된 Room 엔티티 반환
        return savedRoom;
    }

    // 방 입장 검증
    @Transactional
    public ResponseRoomCheckDTO enterRoomCheck(RequestRoomCheckDTO requestRoomCheckDTO) {
        String currentUserEmail = requestRoomCheckDTO.getUserId();

        Room room = roomRepository.findById(requestRoomCheckDTO.getRoomId().intValue())
                .orElseThrow(() -> new RoomNotFoundException("방을 찾을 수 없습니다."));

        // 사용자 확인
        Optional<InRoomUser> inRoomUserOptional = inRoomUserRepository.findByRoom_IdAndUser_Email(room.getId(), currentUserEmail);

        // MASTER가 아닌 경우에만 추가 검증
        if (inRoomUserOptional.isEmpty() || inRoomUserOptional.get().getRole() != Role.MASTER) {
            validateRoom(room, requestRoomCheckDTO);

            // 입장 처리
            if (inRoomUserOptional.isEmpty()) {
                InRoomUser inRoomUser = createInRoomUser(currentUserEmail, room);
                inRoomUserRepository.save(inRoomUser);
            }
        }

        List<InRoomUser> inRoomUsers = inRoomUserRepository.findAllByRoom_Id(requestRoomCheckDTO.getRoomId());
        return new ResponseRoomCheckDTO(room.getId(), room.getTitle(),
                inRoomUsers.stream()
                        .map(user -> new RoomListDTO.UserDTO(user.getUser().getNickname()))
                        .collect(Collectors.toList()), null); // 메시지 없이 반환
    }

    private void validateRoom(Room room, RequestRoomCheckDTO requestRoomCheckDTO) {
        // 방 인원 제한 확인
        List<InRoomUser> inRoomUsers = inRoomUserRepository.findAllByRoom_Id(requestRoomCheckDTO.getRoomId());
        if (inRoomUsers.size() >= 6) {
            throw new RoomFullException("방 인원이 초과되었습니다.");
        }

        // 비밀번호 확인
        if (!room.getPassword().isEmpty() && !room.getPassword().equals(requestRoomCheckDTO.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 틀렸습니다.");
        }

        // 게임 상태 확인
        if (room.getState() == GameState.START) {
            throw new GameAlreadyStartedException("게임이 이미 시작되었습니다.");
        }
    }

    private InRoomUser createInRoomUser(String userEmail, Room room) {
        return InRoomUser.builder()
                .id(new InRoomUserId(userEmail, room.getId()))
                .room(room)
                .user(userRepository.findById(userEmail)
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다.")))
                .role(Role.MEMBER)
                .build();
    }
}