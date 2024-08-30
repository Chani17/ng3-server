package com.nggg.ng3.controller;

import com.nggg.ng3.dto.AvatarComponentDTO;
import com.nggg.ng3.service.AvatarComponentService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // 이 컨트롤러에 대해 CORS 허용
@RestController
@RequestMapping("/api/avatar-components")
public class AvatarComponentController {

    private final AvatarComponentService avatarComponentService;

    public AvatarComponentController(AvatarComponentService avatarComponentService) {
        this.avatarComponentService = avatarComponentService;
    }

    @GetMapping
    public List<AvatarComponentDTO> getAllAvatarComponents() {
        return avatarComponentService.getAllAvatarComponents();
    }
}