package com.nggg.ng3.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * [김찬희] : 그림의 좋아요를 관리하는 Entity 작성
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "LIKES")
public class Like extends BaseEntity {

    @EmbeddedId
    @Getter(AccessLevel.NONE)
    private LikeId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("imageId")
    @JoinColumn(name = "IMAGE_ID", nullable = false)
    private Image image;

}
