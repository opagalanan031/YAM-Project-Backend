package com.cognizant.yam.payload.request;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import com.cognizant.yam.entity.Address;

import lombok.Data;

@Data
@Validated
public class RegisterUserRequest {
	@NotBlank
	private String email;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	private String phone;
	@NotBlank
	private String role;
	
	private Address address;
}
