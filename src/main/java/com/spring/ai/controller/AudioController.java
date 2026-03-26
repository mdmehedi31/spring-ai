package com.spring.ai.controller;


import com.spring.ai.chat.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/audio")
public class AudioController {

    @Autowired
    private AudioService audioService;


    @GetMapping("/convert-to-text")
    public ResponseEntity<String> convertAudioToText(@RequestParam("file") MultipartFile file,
                                                     @RequestParam(value = "language", required = false) String language) {
        String response = this.audioService.convertVoiceToText(file, language);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
