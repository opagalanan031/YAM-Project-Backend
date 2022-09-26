package com.cognizant.yam.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.yam.entity.User;
import com.cognizant.yam.enums.Role;
import com.cognizant.yam.payload.request.LoginUserRequest;
import com.cognizant.yam.payload.request.RegisterUserRequest;
import com.cognizant.yam.payload.request.UpdateAddressRequest;
import com.cognizant.yam.payload.request.UpdateDetailsRequest;
import com.cognizant.yam.payload.response.JsonMessageResponse;
import com.cognizant.yam.payload.response.JwtResponse;
import com.cognizant.yam.payload.response.RegisterUserResponse;
import com.cognizant.yam.security.JwtUtils;
import com.cognizant.yam.security.UserDetailsImpl;
import com.cognizant.yam.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterUserRequest request) {
		
		if(userService.existsByUsername(request.getUsername()) || userService.existsByEmail(request.getEmail())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonMessageResponse("User already in system"));
		}
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setPhone(request.getPhone());
		
		if(request.getRole().equalsIgnoreCase("CORE")) {
			user.setRole(Role.CORE_MEMBER);
		} else if(request.getRole().equalsIgnoreCase("REGULAR")) {
			user.setRole(Role.REGULAR_MEMBER);
		} else {
			throw new RuntimeException("Invalid role");
		}
		
		user.setAddress(request.getAddress());
		
		User newUser = userService.register(user);
		
		RegisterUserResponse response = new RegisterUserResponse();
		response.setId(newUser.getId());
		response.setEmail(newUser.getEmail());
		response.setUsername(newUser.getUsername());
		response.setFirstName(newUser.getFirstName());
		response.setLastName(newUser.getLastName());
		response.setRole(newUser.getRole());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginUserRequest request) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtUtils.generateToken(auth);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) auth.getPrincipal();
		
		List<String> roles = userDetailsImpl.getAuthorities().stream()
				.map(e -> e.getAuthority()).collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetailsImpl.getId(), userDetailsImpl.getEmail(), userDetailsImpl.getUsername(),
				userDetailsImpl.getFirstName(), userDetailsImpl.getLastName(), roles));
	}
	
	@GetMapping("/get-all-users")
	public ResponseEntity<?> getUsers() {
		List<User> users = userService.getUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	@GetMapping("/get-user/{id}")
	public ResponseEntity<?> getUserById(@PathVariable int id) {
		User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User with ID: "+ id + " not found"));
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@PutMapping("/update-details/{id}")
	public ResponseEntity<?> updateUserDetails(@PathVariable int id, @RequestBody UpdateDetailsRequest request) {
		User updateUser = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User with ID: "+ id + " not found"));
		updateUser.setEmail(request.getEmail());
		updateUser.setUsername(request.getUsername());
		updateUser.setPassword(passwordEncoder.encode(request.getPassword()));
		updateUser.setFirstName(request.getFirstName());
		updateUser.setLastName(request.getLastName());
		updateUser.setPhone(request.getPhone());
		
		userService.updateUser(updateUser);
		
		return ResponseEntity.status(HttpStatus.OK).body(new JsonMessageResponse("User Details updated successfully!"));
	}
	
	@PutMapping("/update-address/{id}")
	public ResponseEntity<?> updateUserAddress(@PathVariable int id, @RequestBody UpdateAddressRequest request) {
		User updateUser = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User with ID: "+ id + " not found"));
		updateUser.getAddress().setAddress(request.getAddress());
		updateUser.getAddress().setCity(request.getCity());
		updateUser.getAddress().setState(request.getState());
		
		userService.updateUser(updateUser);
		
		return ResponseEntity.status(HttpStatus.OK).body(new JsonMessageResponse("Address updated successfully!"));
	}
	
	@DeleteMapping("/delete-user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
		if(!userService.existsById(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonMessageResponse("User with ID: "+ id + " not found"));
		}
		
		userService.deleteUser(id);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
