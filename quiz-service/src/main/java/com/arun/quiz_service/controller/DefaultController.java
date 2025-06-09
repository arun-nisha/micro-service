package com.arun.quiz_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @GetMapping({"", "/"})
    public ResponseEntity<String> getAllQuestions(){
        return new ResponseEntity<>("Default Controller is invoked", HttpStatus.OK);
    }
}
