package com.cognizant.yam.payload.request;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
public class LoginUserRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
