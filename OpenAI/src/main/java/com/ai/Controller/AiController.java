package com.ai.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ai.DTO.AiRequest;
import com.ai.DTO.AiResponse;
import com.ai.Service.AiService;

@RestController
@RequestMapping("/bot")
@CrossOrigin(origins = "*")
public class AiController {
	
	@Value("${openai.model}")
	private String model;
	
	@Value("${openai.url}")
	private String url;
	
	@Autowired
	private AiService aiService;
	
	@Autowired
	private RestTemplate template;

//	@GetMapping("/chat")
//	public String chat(@RequestParam("prompt") String prompt) {
//		AiRequest request = new AiRequest(model, prompt);
//		AiResponse response = template.postForObject(url, request, AiResponse.class);
//		return response.getChoices().get(0).getMessage().getContent();
//	}
	
	@GetMapping("/ask")
	public ResponseEntity<String> chat(@RequestParam("prompt") String choice) {
		return new ResponseEntity<String>(aiService.chooseChoice(choice),HttpStatus.OK);
	}
	
	@GetMapping("/result")
	public ResponseEntity<HashMap<String, String>> result() {
		return new ResponseEntity<HashMap<String,String>>(aiService.printConvo(),HttpStatus.OK);
	}
	
	@PostMapping("/reply")
	public ResponseEntity<String> reply(@RequestParam("prompt") String answer) {
		return new ResponseEntity<String>(aiService.myAnswer(answer),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/feedback")
	public ResponseEntity<String> feedback(){
		return new ResponseEntity<String>(aiService.giveFeedback(),HttpStatus.OK);
	}
	
}
