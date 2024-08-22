package com.nggg.ng3.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IN_ROOM_USERS")
public class InRoomUser {

    @EmbeddedId
    @Getter(AccessLevel.NONE)
    private InRoomUserId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "ROOM_ID", nullable = false)
    private Room room;

    @Enumerated(EnumType.STRING)
    private Role role;
}
