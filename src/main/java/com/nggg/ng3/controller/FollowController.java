package com.nggg.ng3.controller;

import com.nggg.ng3.dto.UserInfoDTO;
import com.nggg.ng3.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // 사용자 검색
    @GetMapping("/usersearch")
    public ResponseEntity<List<UserInfoDTO>> userSearch(@RequestParam String nickname) {
        Optional<UserInfoDTO> search = followService.searchUser(nickname)
                .map(u -> new UserInfoDTO(u.getEmail(), u.getProfile_image(), u.getNickname(), followService.getUserTotalLikes(u.getEmail())));
        return search.map(u -> ResponseEntity.ok(List.of(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 팔로우 목록 가져오기
    @GetMapping("/following")
    public ResponseEntity<List<UserInfoDTO>> followingList(@RequestParam String userEmail) {
        List<UserInfoDTO> follow = followService.getFollowList(userEmail).stream()
                .map(user -> new UserInfoDTO(user.getEmail(), user.getNickname(), user.getProfile_image(), followService.getUserTotalLikes(user.getEmail())))
                .toList();
        return ResponseEntity.ok(follow);
    }

    // 팔로우
    @PostMapping("/follow")
    public ResponseEntity<Void> followUser(@RequestParam String userEmail, @RequestParam String followingEmail) {
        try {
            followService.follow(userEmail, followingEmail);
            return ResponseEntity.ok().build();
        } catch(IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // 언팔로우
    @DeleteMapping("/unfollow")
    public ResponseEntity<Void> unfollowUser(@RequestParam String userEmail, @RequestParam String followingEmail) {
        try {
            followService.unfollow(userEmail, followingEmail);
            return ResponseEntity.ok().build();
        } catch(IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
