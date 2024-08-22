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

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FOLLOWS")
public class Follow {

    @EmbeddedId
    @Getter(AccessLevel.NONE)
    private FollowId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("followingId")
    @JoinColumn(name = "FOLLOWING_ID", nullable = false)
    private User following;
}
