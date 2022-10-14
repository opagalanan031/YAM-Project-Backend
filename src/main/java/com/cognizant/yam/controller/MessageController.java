package com.cognizant.yam.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.yam.entity.Message;
import com.cognizant.yam.service.MessageService;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {
	
	@Autowired
	MessageService msgService;
	
	@PostMapping("/send-message")
	public ResponseEntity<?> sendMessage(@Valid @RequestBody Message msgRequest) {
		
		Message msg = new Message();
		msg.setMsgText(msgRequest.getMsgText());
		msg.setSender(msgRequest.getSender());
		msgService.sendMessage(msg);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(msg);
	}
}
