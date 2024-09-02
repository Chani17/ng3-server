// 한수민: 사용자 검색, 팔로우 리스트 가져오기, 팔로우, 언팔로우
package com.nggg.ng3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nggg.ng3.dto.UserInfoDTO;
import com.nggg.ng3.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class FollowController {
    private final FollowService followService;
    private static final Logger log = LoggerFactory.getLogger(FollowController.class);

    // 사용자 검색
    @GetMapping("/usersearch")
    public ResponseEntity<List<UserInfoDTO>> userSearch(@RequestParam String nickname) {
        Optional<UserInfoDTO> search = followService.searchUser(nickname)
                .map(u -> new UserInfoDTO(u.getEmail(), u.getProfile_image(), u.getNickname(), followService.getUserTotalLikes(u.getEmail())));
        return search.map(u -> ResponseEntity.ok(List.of(u)))
                .orElseGet(() -> ResponseEntity.ok(List.of()));
    }

    // 팔로우 목록 가져오기
    @GetMapping("/following")
    public ResponseEntity<List<UserInfoDTO>> followingList(@RequestParam String userEmail) {
        try {
            List<UserInfoDTO> follow = followService.getFollowList(userEmail).stream()
                    .map(u -> new UserInfoDTO(u.getEmail(), u.getProfile_image(), u.getNickname(), followService.getUserTotalLikes(u.getEmail())))
                    .toList();
            return ResponseEntity.ok(follow);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    // 팔로우
    @PostMapping("/followuser")
    public ResponseEntity<Void> followUser(@RequestBody Map<String, String> request) {
        try {
            String userEmail = request.get("userEmail");
            String followingEmail = request.get("followingEmail");
            followService.follow(userEmail, followingEmail);
            return ResponseEntity.ok().build();
        } catch(IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // 언팔로우
    @PostMapping("/unfollowuser")
    public ResponseEntity<Void> unfollowUser(@RequestBody Map<String, String> request) {
        try {
            String userEmail = request.get("userEmail");
            String followingEmail = request.get("followingEmail");
            followService.unfollow(userEmail, followingEmail);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
