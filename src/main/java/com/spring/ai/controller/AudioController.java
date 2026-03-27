package com.spring.ai.controller;


import com.spring.ai.chat.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


    @GetMapping("/convert-text-to-voice")
    public ResponseEntity<Resource> convertTextToAudio(@RequestParam("prompt") String prompt) throws IOException {
        Resource response = this.audioService.convertTextToSpace(prompt);
        return ResponseEntity.ok().
                contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(response.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("ai-voice.mp3").build().toString())
                .body(response);
    }
}
