package com.nggg.ng3.repository;

import com.nggg.ng3.entity.InRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InRoomUserRepository extends JpaRepository<InRoomUser, Integer> {
    List<InRoomUser> findByRoomId(Long roomId);
}