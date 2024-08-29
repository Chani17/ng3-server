package com.nggg.ng3.repository;

import com.nggg.ng3.entity.Follow;
import com.nggg.ng3.entity.FollowId;
import com.nggg.ng3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    List<Follow> findFollowingByUser(User user);
}
