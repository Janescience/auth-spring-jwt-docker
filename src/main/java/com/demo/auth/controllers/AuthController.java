package com.demo.auth.controllers;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.auth.entity.app.Role;
import com.demo.auth.entity.app.User;
import com.demo.auth.entity.enums.EMemberType;
import com.demo.auth.entity.enums.ERole;
import com.demo.auth.payload.request.SigninRequest;
import com.demo.auth.payload.request.SignupRequest;
import com.demo.auth.payload.response.JwtResponse;
import com.demo.auth.payload.response.MessageResponse;
import com.demo.auth.repository.MemberTypeRepository;
import com.demo.auth.repository.RoleRepository;
import com.demo.auth.repository.UserRepository;
import com.demo.auth.security.services.UserDetailsImpl;
import com.demo.auth.util.DateUtil;
import com.demo.auth.util.JwtUtil;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.US);
	
    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	MemberTypeRepository memberTypeRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest req) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getPhoneNumber(), 
												 userDetails.getSalary(), 
												 userDetails.getAddress(), 
												 userDetails.getRefCode(), 
												 userDetails.getMemberType(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest req){
		if (userRepository.existsByUsername(req.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Username is already taken!","400","error",null));
		}

		if (userRepository.existsByPhoneNumber(req.getPhoneNumber())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("PhoneNumber is already in use!","400","error",null));
		}

		String refCode = DATE_FORMAT.format(DateUtil.getCurrentDate()) + req.getPhoneNumber().substring(req.getPhoneNumber().length() - 4);

		if (userRepository.existsByRefCode(refCode)) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("RefCode (YYYYMMDD)(4 last digit phone number) is already in use!","400","error",null));
		}

		User user = new User();
		user.setUsername(req.getUsername());
		user.setPassword(encoder.encode(req.getPassword()));
		user.setPhoneNumber(req.getPhoneNumber());
		user.setAddress(req.getAddress());
		user.setRefCode(refCode);
		user.setSalary(req.getSalary());

        if (user.getSalary() >= 80000) {
            user.setMemberType(memberTypeRepository.findByName(EMemberType.PLATINUM));
        } else if (user.getSalary() >= 40000) {
			user.setMemberType(memberTypeRepository.findByName(EMemberType.GOLD));
        } else{
			user.setMemberType(memberTypeRepository.findByName(EMemberType.SILVER));
        }

		Set<String> strRoles = req.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully.","200","success",user));

	}
}
