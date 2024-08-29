package com.nggg.ng3.service;

import com.nggg.ng3.entity.Follow;
import com.nggg.ng3.entity.FollowId;
import com.nggg.ng3.entity.User;
import com.nggg.ng3.repository.FollowRepository;
import com.nggg.ng3.repository.LikeRepository;
import com.nggg.ng3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    // 사용자 검색
    public Optional<User> searchUser(String search) {
        return userRepository.findByNickname(search);
    }

    // 팔로우 목록 가져오기
    public List<User> getFollowList(String userEmail) {
        User user = userRepository.findById(userEmail).orElseThrow(() -> new RuntimeException("유저 정보를 찾을 수 없습니다."));
        return followRepository.findFollowingByUser(user).stream().map(Follow::getFollowing).toList();
    }

    // 팔로우 확인
    public boolean isFollowing(String userEmail, String followingEmail) {
        FollowId followId = new FollowId(userEmail, followingEmail);
        return followRepository.existsById(followId);
    }

    // 팔로우
    @Transactional
    public void follow(String userEmail, String followingEmail) {
        if (isFollowing(userEmail, followingEmail)) {
            throw new IllegalStateException("이미 팔로우 중입니다.");
        }
        User user = userRepository.findById(userEmail).orElseThrow(() -> new RuntimeException("유저 정보를 찾을 수 없습니다."));
        User following = userRepository.findById(followingEmail).orElseThrow(() -> new RuntimeException("팔로우할 정보를 찾을 수 없습니다."));

        Follow follow = Follow.builder()
                .id(new FollowId(userEmail, followingEmail))
                .user(user)
                .following(following)
                .build();
        followRepository.save(follow);
    }

    // 언팔로우
    @Transactional
    public void unfollow(String userEmail, String followingEmail) {
        if (!isFollowing(userEmail, followingEmail)) {
            throw new IllegalStateException("팔로우되어 있지 않습니다.");
        }
        FollowId followId = new FollowId(userEmail, followingEmail);
        followRepository.deleteById(followId);
    }

    // 사용자의 총 좋아요 수 가져오기
    public Long getUserTotalLikes(String userEmail) {
        return likeRepository.countLikeByUserEmail(userEmail);
    }
}
