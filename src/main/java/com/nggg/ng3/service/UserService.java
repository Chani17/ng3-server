package com.nggg.ng3.service;

import com.nggg.ng3.entity.User;
import com.nggg.ng3.repository.UserRepository;
import com.nggg.ng3.repository.WearingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final WearingRepository wearingRepository;

    /*해당 ID의 유저가 db에 없다면 새로 넣기*/
    public void addUser(String loginUserId){
        Optional<User> user = userRepository.findById(loginUserId);
        if(user.isEmpty()){
            User newUser = User.builder()
                    .email(loginUserId)
                    .nickname(loginUserId)
                    .build();
            userRepository.save(newUser);
        }
    }

    public boolean userExists(String userId) {
        return userRepository.existsById(userId);
    }

    @Transactional
    public void deleteWearingByUserId(String userId) {
        wearingRepository.deleteByUserId_Email(userId);
    }
}
