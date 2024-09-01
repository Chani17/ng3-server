package com.nggg.ng3.service;


import com.nggg.ng3.dto.WearingDTO;
import com.nggg.ng3.entity.AvatarComponent;
import com.nggg.ng3.entity.User;
import com.nggg.ng3.entity.Wearing;
import com.nggg.ng3.entity.WearingId;
import com.nggg.ng3.repository.AvatarComponentRepository;
import com.nggg.ng3.repository.UserRepository;
import com.nggg.ng3.repository.WearingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WearingService {
    private final WearingRepository wearingRepository;

    private final UserRepository userRepository;

    private final AvatarComponentRepository avatarComponentRepository;

    public WearingService(WearingRepository wearingRepository, UserRepository userRepository, AvatarComponentRepository avatarComponentRepository) {
        this.wearingRepository = wearingRepository;
        this.userRepository = userRepository;
        this.avatarComponentRepository = avatarComponentRepository;
    }
    /**[박혁진] : userId와로 현재 착용한하고 있는 옷을 불러오는 메서드*/
    public List<WearingDTO> getWearingComponentsByUserId(String userId) {
        List<Wearing> wearings = wearingRepository.findByUserId_Email(userId);

        return wearings.stream().map(wearing -> new WearingDTO(
                wearing.getAvatarComponent().getId(),
                wearing.getAvatarComponent().getImageUrl(),
                wearing.getAvatarComponent().getAvatarComponentCategory().getId()
        )).collect(Collectors.toList());
    }

    /**[박혁진] : userId와 Component 번호로 Wearing에 현재 착용한 옷을 저장하는 메서드*/
    @Transactional
    public Wearing saveWearing(String userId, Long avatarComponentId) {
        // User와 AvatarComponent를 먼저 조회합니다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AvatarComponent avatarComponent = avatarComponentRepository.findById(avatarComponentId)
                .orElseThrow(() -> new RuntimeException("Avatar Component not found"));

        // WearingId 객체 생성
        WearingId wearingId = new WearingId(user.getEmail(), avatarComponent.getId());

        // Wearing 객체 생성 및 저장
        Wearing wearing = Wearing.builder()
                .id(wearingId)
                .userId(user)
                .avatarComponent(avatarComponent)
                .build();

        return wearingRepository.save(wearing);
    }

    /**[박혁진] : userId로 현재 착용중인 컴포넌트를 제거하는 메서드*/
    @Transactional
    public void deleteWearingByUserId(String userId) {
        wearingRepository.deleteByUserId_Email(userId);
    }


}
