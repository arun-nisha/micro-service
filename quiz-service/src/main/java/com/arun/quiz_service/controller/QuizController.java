package com.arun.quiz_service.controller;

import com.arun.quiz_service.model.QuestionWrapper;
import com.arun.quiz_service.model.UserResponse;
import com.arun.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping({"", "/"})
    ResponseEntity<Map<Integer,List<QuestionWrapper>>> getAllQuiz(){
        return this.quizService.getQuizQuestions();
    }

    @GetMapping("/{id}")
    ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable int id){
        return this.quizService.getQuizById(id);
    }

    @PostMapping("create")
    ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam String title, @RequestParam int numQuests){
        return this.quizService.createQuiz(category,title, numQuests);
    }

    @PostMapping("submit/{id}")
    ResponseEntity<Integer> submitQuiz(@PathVariable("id") int quizId,@RequestBody List<UserResponse> userResponseList){
        return this.quizService.checkUserResponse(quizId, userResponseList);
    }
}
