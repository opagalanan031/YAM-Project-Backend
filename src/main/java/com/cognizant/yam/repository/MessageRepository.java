package com.cognizant.yam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.yam.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
