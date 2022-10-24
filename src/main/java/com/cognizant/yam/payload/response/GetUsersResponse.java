package com.cognizant.yam.payload.response;

import lombok.Data;

@Data
public class GetUsersResponse {
	private Integer id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;

}
