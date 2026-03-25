package com.spring.ai.chat.service;

import com.spring.ai.dto.TodoDTO;
import com.spring.ai.dto.TodoListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final ChatClient chatClient;

    @Value("classpath:/prompts/todo-list.st")
    private Resource todoPrompt;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemPrompt;

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

    public List<TodoListDTO> getTodoListByListJsonFormat(String message) {
        String promptValue = "Create a todo list on this topics {message} ";
        PromptTemplate template = new PromptTemplate(promptValue);
        Prompt prompt = template.create(Map.of("message",message));
        return chatClient.prompt(prompt).call().entity(new ParameterizedTypeReference<List<TodoListDTO>>() {});

    }

    public TodoDTO getTodoListByJsonFormat(String message) {
        String promptValue = "Create a todo list on this topics {message} " +
                "{format}";
        BeanOutputConverter<TodoDTO> converter = new BeanOutputConverter<>(new ParameterizedTypeReference<TodoDTO>(){});

        PromptTemplate template = new PromptTemplate(promptValue);
        Prompt prompt = template.create(Map.of("message",message, "format",converter.getFormat()));

        Generation result = chatClient.prompt(prompt).call().chatResponse().getResult();
        return  converter.convert(result.getOutput().getText());
    }

    public String getMyPlanListWithRole(String message) {

        String systemMessageValue="List should be serial and use ordering number like number of " +
                "or any types of symbol, and give me html format without Markdown, all response should be in the html tag, " +
                "I will use this for showing in my application";
        UserMessage userMessage = new UserMessage(message);
        SystemMessage systemMessage= new SystemMessage(systemMessageValue);

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        return  chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();
    }

    public String getTodoPlanList(String message) {
        String promptValue = "Create a todo list on this topics {message}";
        String systemMessageValue="List should be serial and use ordering number like number of " +
                "or any types of symbol, and give me html format without Markdown, all response should be in the html tag, " +
                "I will use this for showing in my application";

        PromptTemplate promptTemplate = new PromptTemplate(promptValue);
        Prompt prompt = promptTemplate.create(Map.of("message",message));
        SystemMessage systemMessage= new SystemMessage(systemMessageValue);

        return chatClient.prompt(prompt).system(systemMessageValue).
                call().chatResponse()
                .getResult()
                .getOutput()
                .getText();
    }

}
