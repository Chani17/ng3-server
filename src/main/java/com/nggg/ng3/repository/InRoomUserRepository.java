// 이하린 : 방과 사용자 정보를 기반으로 InRoomUser Entity를 조회하기 위한 메서드를 정의한 리포지토리

package com.nggg.ng3.repository;

import com.nggg.ng3.entity.InRoomUser;
import com.nggg.ng3.entity.InRoomUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InRoomUserRepository extends JpaRepository<InRoomUser, InRoomUserId> {

    // 특정 방에 포함된 모든 InRoomUser 리스트 조회
    List<InRoomUser> findByRoomId(Long roomId);

    // 특정 방과 사용자에 대한 InRoomUser 조회
    Optional<InRoomUser> findByRoom_IdAndUser_Email(Long roomId, String userId);
}