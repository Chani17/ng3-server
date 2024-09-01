// 이하린 : 게임방 생성, 방 목록 출력, 방 입장 검증 서비스

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

    // 방 목록 가져온 후 DTO로 변환
    public List<RoomListDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream()
                .map(room -> {
                    // 방에 포함된 사용자 정보 가져오기
                    List<RoomListDTO.UserDTO> users = inRoomUserRepository.findByRoomId(room.getId()).stream()
                            .map(inRoomUser -> RoomListDTO.UserDTO.builder()
                                    .email(inRoomUser.getUser().getEmail())
                                    .nickname(inRoomUser.getUser().getNickname())
                                    .profile_image(inRoomUser.getUser().getProfile_image())
                                    .build())
                            .collect(Collectors.toList());

                    // 방 정보를 RoomListDTO로 변환하여 반환
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

        // DTO 정보를 기반으로 Room 엔티티 생성
        Room room = Room.builder()
                .title(createRoomDTO.getTitle())
                .password(createRoomDTO.getPassword())
                .state(GameState.READY) // 기본 상태를 설정
                .build();

        // 생성된 Room 엔티티를 DB에 저장
        Room savedRoom = roomRepository.save(room);

        // 현재 로그인 된 사용자 정보를 DB에서 가져오기
        User currentUser = userRepository.findById(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 방과 사용자 정보를 InRoomUser 엔티티에 저장
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

        // 요청된 방 ID로 방 정보 조회
        Room room = roomRepository.findById(requestRoomCheckDTO.getRoomId().intValue())
                .orElseThrow(() -> new RoomNotFoundException("방을 찾을 수 없습니다."));

        // 현재 사용자가 해당 방에 있는지 확인
        Optional<InRoomUser> inRoomUserOptional = inRoomUserRepository.findByRoom_IdAndUser_Email(room.getId(), currentUserEmail);

        // 방장이 아닌 경우 추가 검증 수행
        if (inRoomUserOptional.isEmpty() || inRoomUserOptional.get().getRole() != Role.MASTER) {
            validateRoom(room, requestRoomCheckDTO);

            // 방에 처음 입장하는 경우 InRoomUser 생성 및 저장
            if (inRoomUserOptional.isEmpty()) {
                InRoomUser inRoomUser = createInRoomUser(currentUserEmail, room);
                inRoomUserRepository.save(inRoomUser);
            }
        }

        // 방에 포함된 모든 사용자 정보를 DTO로 변환하여 반환
        List<RoomListDTO.UserDTO> userDTOList = inRoomUserRepository.findByRoomId(requestRoomCheckDTO.getRoomId()).stream()
                .map(user -> RoomListDTO.UserDTO.builder()
                        .email(user.getUser().getEmail())
                        .nickname(user.getUser().getNickname())
                        .profile_image(user.getUser().getProfile_image())
                        .build())
                .collect(Collectors.toList());

        // 검증 결과와 함께 응답 DTO 반환
        return new ResponseRoomCheckDTO(room.getId(), room.getTitle(), userDTOList, null); // 메시지 없이 반환
    }

    // 방 입장 검증 로직
    private void validateRoom(Room room, RequestRoomCheckDTO requestRoomCheckDTO) {
        // 방의 인원이 6명 이상일 경우 예외 발생
        List<InRoomUser> inRoomUsers = inRoomUserRepository.findByRoomId(requestRoomCheckDTO.getRoomId());
        if (inRoomUsers.size() >= 6) {
            throw new RoomFullException("방 인원이 초과되었습니다.");
        }

        // 방의 비밀번호가 일치하지 않을 경우 예외 발생
        if (!room.getPassword().isEmpty() && !room.getPassword().equals(requestRoomCheckDTO.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 틀렸습니다.");
        }

        // 방의 상태가 이미 게임 시작 상태일 경우 예외 발생
        if (room.getState() == GameState.START) {
            throw new GameAlreadyStartedException("게임이 이미 시작되었습니다.");
        }
    }

    // 새로운 InRoomUser 엔티티 생성
    private InRoomUser createInRoomUser(String userEmail, Room room) {
        return InRoomUser.builder()
                .id(new InRoomUserId(userEmail, room.getId())) // 복합 키 설정
                .room(room)
                .user(userRepository.findById(userEmail)
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다.")))
                .role(Role.MEMBER) // 기본 사용자 역할 부여
                .build();
    }
}