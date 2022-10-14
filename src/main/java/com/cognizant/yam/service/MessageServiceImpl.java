package com.cognizant.yam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.yam.entity.Message;
import com.cognizant.yam.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	MessageRepository msgRepository;
	
	@Override
	public Message sendMessage(Message msg) {
		return msgRepository.save(msg);
	}

	@Override
	public List<Message> getAllMessages() {
		return msgRepository.findAll();
	}

}
