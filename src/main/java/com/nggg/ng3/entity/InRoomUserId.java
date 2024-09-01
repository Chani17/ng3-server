package com.nggg.ng3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * [김찬희] : 사용자 밤 참여를 나타내는 복합 키 정의
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class InRoomUserId implements Serializable {

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long roomId;
}
