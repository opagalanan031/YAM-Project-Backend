package com.cognizant.yam.payload.response;

import com.cognizant.yam.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserResponse {
	private Integer id;
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	private Role role;
}
