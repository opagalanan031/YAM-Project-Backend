package com.cognizant.yam.payload.request;

import lombok.Data;

@Data
public class UpdateDetailsRequest {
	private String email;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
}
