package com.spring.ai.controller;


import com.spring.ai.chat.service.ChatService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping("/lets-talk")
    public ResponseEntity<String> letsTalk(@RequestParam String message){
        String response = chatService.letsTalk(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/create-todo")
    public ResponseEntity<String> createTodoList(@RequestParam String message){
        String response = chatService.getMyPlanList(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
