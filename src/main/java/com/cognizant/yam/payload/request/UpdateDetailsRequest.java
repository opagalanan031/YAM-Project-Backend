package com.cognizant.yam.payload.request;

import lombok.Data;

@Data
public class UpdateDetailsRequest {
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	private String phone;
}
