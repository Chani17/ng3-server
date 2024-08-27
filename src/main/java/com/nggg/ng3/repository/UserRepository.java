package com.nggg.ng3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nggg.ng3.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}
