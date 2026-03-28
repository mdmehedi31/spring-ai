package com.spring.ai.controller;


import com.spring.ai.chat.service.BDConstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BGCController {

    @Autowired
    private BDConstitutionService bdConstitutionService;

    @RequestMapping("bgc/ask")
    public ResponseEntity<String> askAboutBdConstitution(@RequestParam("prompt") String prompt) {
        String response = this.bdConstitutionService.askToBgc(prompt);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
