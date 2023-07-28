package com.ai.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AiRequest {
	
	private String model;
	private List<Message> messages;
	
	
	public AiRequest(String model, String prompt) {
		this.model = model;
		this.messages = new ArrayList<>();
		this.messages.add(new Message("user", prompt));
	}
	
	
}
