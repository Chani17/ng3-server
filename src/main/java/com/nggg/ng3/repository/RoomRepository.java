// 이하린 : 방 조회 및 생성을 위한 리포지토리

package com.nggg.ng3.repository;

import com.nggg.ng3.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}