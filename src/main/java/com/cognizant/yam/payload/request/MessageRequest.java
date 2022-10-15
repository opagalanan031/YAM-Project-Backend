package com.cognizant.yam.payload.request;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
public class MessageRequest {
	@NotBlank
	private String msgText;
	@NotBlank
	private String sender;
}
