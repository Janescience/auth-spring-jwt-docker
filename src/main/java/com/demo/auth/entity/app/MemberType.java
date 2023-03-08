package com.demo.auth.entity.app;

import com.demo.auth.entity.base.BaseEntity;
import com.demo.auth.entity.enums.EMemberType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Entity
@Data
public class MemberType extends BaseEntity{

    @Enumerated(EnumType.STRING)
	@Column(length = 20)
	private EMemberType name;
}
