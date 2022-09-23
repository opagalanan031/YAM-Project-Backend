package com.cognizant.yam.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cognizant.yam.entity.User;
import com.cognizant.yam.enums.Role;
import com.cognizant.yam.service.UserService;

@Component
public class SetCoreMember {
	private static final String CORE_USERNAME = "yam_core";
	private static final String CORE_PASSWORD = "st_als_yam";
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	private void initializeDB() {
		initializeCore();
	}
	
	private void initializeCore() {
		if(!userService.existsByUsername(CORE_USERNAME)) {
			User core = new User();
			
			core.setFirstName("Core Member");
			core.setUsername(CORE_USERNAME);
			core.setEmail("yam.core@stals.com");
			core.setPassword(CORE_PASSWORD);
			core.setRole(Role.CORE_MEMBER);
			
			userService.register(core);
		}
	}
}
