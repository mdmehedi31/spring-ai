package com.spring.ai.chat.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final ChatClient chatClient;

    @Value("classpath:/prompts/todo-list.st")
    private Resource todoPrompt;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String letsTalk(String message) {
        return chatClient.prompt(message).call().content();

    }

    public String getMyPlanList(String message) {
        PromptTemplate template = new PromptTemplate(todoPrompt);

        Prompt prompt = template.create(
                Map.of("message",message)
        );

        log.info("The prompt is {}", prompt);
        log.info("Prompt message is : {}", todoPrompt);
        return  chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();
    }
}
