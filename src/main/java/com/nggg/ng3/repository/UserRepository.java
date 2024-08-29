package com.nggg.ng3.repository;

import com.nggg.ng3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
}
