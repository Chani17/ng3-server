package com.nggg.ng3.controller;

import com.nggg.ng3.dto.WearingCreateDTO;
import com.nggg.ng3.dto.WearingDTO;
import com.nggg.ng3.service.WearingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wearings")
@RequiredArgsConstructor
public class WearingController {

    private final WearingService wearingService;



    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user/{userId}")
    public List<WearingDTO> getWearingComponentsByUserId(@PathVariable String userId) {//wearing컴포넌트 가져오는 메서드
        return wearingService.getWearingComponentsByUserId(userId);
    }

    // Wearing 데이터 삭제
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteWearingsByUserId(@PathVariable String userId) {
        wearingService.deleteWearingByUserId(userId);  // 서비스 계층 호출
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Void> saveWearing(@RequestBody WearingCreateDTO wearingCreateDTO) {
        wearingService.saveWearing(wearingCreateDTO.getUserId(), wearingCreateDTO.getAvatarComponent().getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}