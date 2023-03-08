package com.demo.auth.entity.app;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.annotation.*;

import java.util.HashSet;
import java.util.Set;

import com.demo.auth.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(	name = "users")
@Data
public class User extends BaseEntity{

	@Nonnull
	@Column(unique = true)
	private String username;

	@Nonnull
	@JsonIgnore
	private String password;

	@Column(length = 1000)
	private String address;

	@Nonnull
	@Column(unique = true,length = 10)
	private String phoneNumber;

	@Nonnull
	@Column(unique = true,length = 12)
	private String refCode;

	@Nonnull
	private Double salary;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberType")
	private MemberType memberType;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
}
