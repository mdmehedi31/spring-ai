package com.spring.ai.controller;


import com.spring.ai.chat.service.ImageChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/img-chat")
public class ImageController {

    @Autowired
    private ImageChatService imageChatService;


    @GetMapping("/convert-to-text")
    public ResponseEntity<String> convertImageToText(){
        String resposne =imageChatService.convertImageToText();
        return new ResponseEntity<>(resposne, HttpStatus.OK);
    }
}
