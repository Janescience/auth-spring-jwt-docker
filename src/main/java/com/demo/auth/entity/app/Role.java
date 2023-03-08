package com.demo.auth.entity.app;

import com.demo.auth.entity.base.BaseEntity;
import com.demo.auth.entity.enums.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity{

    @Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
    
}
