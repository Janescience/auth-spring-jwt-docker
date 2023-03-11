package com.demo.auth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.auth.security.services.UserDetailsServiceImpl;

import com.demo.auth.repository.RoleRepository;
import com.demo.auth.repository.UserRepository;
import com.demo.auth.entity.app.User;
import com.demo.auth.entity.app.Role;
import com.demo.auth.entity.enums.ERole;
import com.demo.auth.payload.response.MessageResponse;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> allAccess() {
        List<User> users = userRepository.findAll();
		return ResponseEntity.ok(new MessageResponse("Get all users", "200", "success", users));
	}
	
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> userAccess() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = userDetailsService.loadUserByUsername(userDetails.getUsername());
		return ResponseEntity.ok(new MessageResponse("Get data user", "200", "success", user));
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<?> moderatorAccess() {
		Role role = roleRepository.findByName(ERole.ROLE_MODERATOR).get();
		List<User> users = userRepository.findByRoles(role);
		return ResponseEntity.ok(new MessageResponse("Get moderator users", "200", "success", users));
	}
}
