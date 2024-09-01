package com.nggg.ng3.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [김찬희] : 아바타의 현재 착장과 관련된 복합키 클래스
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class WearingId implements Serializable {

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private Long avatarComponentId;
}
