package com.cognizant.yam.payload.request;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
public class ChangePasswordRequest {
	private String password;
}
