package com.nggg.ng3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nggg.ng3.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNickname(String username);

}
