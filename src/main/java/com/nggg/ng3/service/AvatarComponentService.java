package com.nggg.ng3.service;

import com.nggg.ng3.dto.AvatarComponentDTO;
import com.nggg.ng3.entity.AvatarComponent;
import com.nggg.ng3.repository.AvatarComponentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**[박혁진] : db에 존재하는 모든 아바타 컴포넌트를 불러오는 서비스*/
@Service
public class AvatarComponentService {

    private final AvatarComponentRepository avatarComponentRepository;

    public AvatarComponentService(AvatarComponentRepository avatarComponentRepository) {
        this.avatarComponentRepository = avatarComponentRepository;
    }

    public List<AvatarComponentDTO> getAllAvatarComponents() {
        List<AvatarComponent> components = avatarComponentRepository.findAll();
        return components.stream()
                .map(component -> new AvatarComponentDTO(
                        component.getId(),
                        component.getImageUrl(),
                        component.getAvatarComponentCategory().getId()
                ))
                .collect(Collectors.toList());
    }
}
