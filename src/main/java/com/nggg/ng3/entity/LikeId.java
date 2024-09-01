package com.nggg.ng3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * [김찬희] : 좋아요 관계를 나타내는 복합키 정의
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class LikeId implements Serializable {

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long imageId;
}
