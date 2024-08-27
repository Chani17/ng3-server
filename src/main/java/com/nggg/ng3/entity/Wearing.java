package com.nggg.ng3.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "WEARING")
public class Wearing {

	@EmbeddedId
	@Getter(AccessLevel.NONE)
	private WearingId id;

	@ManyToOne
	@MapsId("avatarId")
	@JoinColumn(name = "AVATAR_ID", nullable = false)
	private Avatar avatar;

	@ManyToOne
	@MapsId("avatarComponentId")
	@JoinColumn(name = "AVATAR_COMPONENT_ID", nullable = false)
	private AvatarComponent avatarComponent;
}
