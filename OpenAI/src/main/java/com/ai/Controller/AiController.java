package com.ai.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ai.DTO.AiRequest;
import com.ai.DTO.AiResponse;

@RestController
@RequestMapping("/bot")
public class AiController {
	
	@Value("${openai.model}")
	private String model;
	
	@Value("${openai.url}")
	private String url;
	
	@Autowired
	private RestTemplate template;

	@GetMapping("/chat")
	public String chat(@RequestParam("prompt") String prompt) {
		AiRequest request = new AiRequest(model, prompt);
		AiResponse response = template.postForObject(url, request, AiResponse.class);
		return response.getChoices().get(0).getMessage().getContent();
	}
}
