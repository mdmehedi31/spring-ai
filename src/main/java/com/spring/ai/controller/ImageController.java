package com.spring.ai.controller;


import com.spring.ai.chat.service.ImageChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("convert-file-to-text")
    public ResponseEntity<String> convertImageToText(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("message") String message){
        String resposne =imageChatService.convertImageToText(file, message);
        return new ResponseEntity<>(resposne, HttpStatus.OK);
    }

    @GetMapping("generate-text-to-image")
    public ResponseEntity<String> generateImageByText(@RequestParam("prompt") String prompt){
        String resposne =imageChatService.generateImageByText(prompt);
        return new ResponseEntity<>(resposne, HttpStatus.OK);
    }
}
