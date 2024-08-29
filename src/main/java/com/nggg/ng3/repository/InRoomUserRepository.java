package com.nggg.ng3.repository;

import com.nggg.ng3.entity.InRoomUser;
import com.nggg.ng3.entity.InRoomUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InRoomUserRepository extends JpaRepository<InRoomUser, InRoomUserId> {

    List<InRoomUser> findAllByRoom_Id(Long roomId);

    List<InRoomUser> findByRoomId(Long roomId);

    // 특정 방과 사용자에 대한 InRoomUser 조회
    Optional<InRoomUser> findByRoom_IdAndUser_Email(Long roomId, String userId);
}