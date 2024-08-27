package com.nggg.ng3.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class WearingId implements Serializable {

	@Column(nullable = false)
	private Long avatarId;

	@Column(nullable = false)
	private Long avatarComponentId;
}
