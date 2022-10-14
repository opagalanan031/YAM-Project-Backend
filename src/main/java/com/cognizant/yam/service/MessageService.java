package com.cognizant.yam.service;

import java.util.List;

import com.cognizant.yam.entity.Message;

public interface MessageService {
	Message sendMessage(Message msg);
	List<Message> getAllMessages();
}
