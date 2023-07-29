package com.ai.Service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ai.DTO.AiRequest;
import com.ai.DTO.AiResponse;

@Service
public class AiService {
	
	@Value("${openai.model}")
	private String model;
	
	@Value("${openai.url}")
	private String url;
	
//	private ArrayList<String> questions = new ArrayList<>(); // [what is java]
//	private ArrayList<String> answers = new ArrayList<>();//[""]
	
	private String ques="";
	private String ans="";
	
	private HashMap<String, String> result = new HashMap<>();
	
	
	@Autowired
	private RestTemplate template;
	
	public HashMap<String, String> printConvo() {
		return result;
	}
	
	public String chooseChoice(String choice) {
		
		if(choice.equalsIgnoreCase("JAVA")) {
			AiRequest request = new AiRequest(model, "Only provide 1 java interview question");
			AiResponse response = template.postForObject(url, request, AiResponse.class);
			String question = response.getChoices().get(0).getMessage().getContent();
			ques=question;
			return question;
		}
		else if(choice.equalsIgnoreCase("MERN")) {
			AiRequest request = new AiRequest(model, "Start a MERN interview with 5 questions and wait for my response after every question");
			AiResponse response = template.postForObject(url, request, AiResponse.class);
			return response.getChoices().get(0).getMessage().getContent();
		}
		else {
			AiRequest request = new AiRequest(model, "Start a Node interview with 5 questions and wait for my response after every question");
			AiResponse response = template.postForObject(url, request, AiResponse.class);
			return response.getChoices().get(0).getMessage().getContent();
		}
	}
	
	public String myAnswer(String answer) {
		ans = answer;
		result.put(ques, ans);
		return "Great click here for next question";
	}
	
	public String giveFeedback() {
		
		if(result.isEmpty()) return "No Data to provide feedback";
		else {
			String query="Provided Genuiene feedback on from 1 to 10 on these questions and answers with points to work on ";
			int index = 1;
			for (Map.Entry<String, String> entry : result.entrySet()) {
	            String key = entry.getKey();
	            String value = entry.getValue();
	            query += "Question "+index +": " + key;
	            query += "Anwser : " + value;
	        }
			
			AiRequest request = new AiRequest(model, query);
			AiResponse response = template.postForObject(url, request, AiResponse.class);
			String feedback = response.getChoices().get(0).getMessage().getContent();
			
			return feedback;
		}
		
		
	}
}
