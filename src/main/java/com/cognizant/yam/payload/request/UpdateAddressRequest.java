package com.cognizant.yam.payload.request;

import lombok.Data;

@Data
public class UpdateAddressRequest {
	private String address;
	private String city;
	private String state;
}
