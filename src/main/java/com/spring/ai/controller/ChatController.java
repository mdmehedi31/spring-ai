package com.spring.ai.controller;


import com.spring.ai.chat.service.ChatService;
import com.spring.ai.dto.TodoDTO;
import com.spring.ai.dto.TodoListDTO;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/create-todo-with-rules")
    public ResponseEntity<String> getMyPlanListWithRoles(@RequestParam String message){
        String response = chatService.getMyPlanListWithRole(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/generate-todo-list")
    public ResponseEntity<String> generateTodoList(@RequestParam String message){
        String response = chatService.getTodoPlanList(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/generate-todo-list-json-format")
    public ResponseEntity<TodoDTO> getJsonFormatTodoList(@RequestParam String message){
        TodoDTO response = chatService.getTodoListByJsonFormat(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/generate-list-json-format")
    public ResponseEntity<List<TodoListDTO>> getJsonFormatList(@RequestParam String message){
        List<TodoListDTO> responseList= chatService.getTodoListByListJsonFormat(message);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
