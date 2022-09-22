package com.cognizant.yam.service;

import java.util.List;
import java.util.Optional;

import com.cognizant.yam.entity.User;

public interface UserService {
	User register(User user);
	List<User> getUsers();
	Optional<User> getUserById(int id);
	User updateUser(User user);
	void deleteUser(int id);
	boolean existsById(int id);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
