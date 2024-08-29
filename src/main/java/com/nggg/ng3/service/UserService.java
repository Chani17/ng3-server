package com.nggg.ng3.service;

import com.nggg.ng3.entity.User;
import com.nggg.ng3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /*해당 ID의 유저가 db에 없다면 새로 넣기*/
    public void addUser(String loginUserId){
        Optional<User> user = userRepository.findById(loginUserId);
        if(user.isEmpty()){
            User newUser = User.builder()
                    .email(loginUserId)
                    .nickname("닉네임")
                    .build();
            userRepository.save(newUser);
        }
    }
}
