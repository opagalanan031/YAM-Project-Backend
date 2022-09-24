package com.cognizant.yam.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Integer id;
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	private List<String> roles;
	
	public JwtResponse(String token, Integer id, String email, String username, String firstName, String lastName,
			List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.email = email;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
	}
	
	
}
