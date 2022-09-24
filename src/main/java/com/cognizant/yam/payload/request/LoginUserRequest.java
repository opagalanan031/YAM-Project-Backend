package com.cognizant.yam.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginUserRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
