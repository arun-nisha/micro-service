package com.arun.question_service.controller;

import com.arun.question_service.model.Question;
import com.arun.question_service.model.QuestionWrapper;
import com.arun.question_service.model.UserResponse;
import com.arun.question_service.service.QuestionService;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    private QuestionService questionService = null;

    QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping({"", "/","allQuestions","allQuestions/"})
    private ResponseEntity<List<Question>> getAllQuestions(){
        return this.questionService.getAllQuestions();
    }

    @GetMapping("category")
    private ResponseEntity<List<Question>> getAllQuestionsByCategory(){
        return this.questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    private ResponseEntity<List<Question>> getAllQuestionsByCategory(@PathVariable String category){
        return this.questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    private ResponseEntity<String> createQuestion(@RequestBody List<Question> questionList){
        return this.questionService.createQuestion(questionList, "SAVE");
    }

    @PutMapping("update")
    private ResponseEntity<String> updateQuestion(@RequestBody List<Question> questionList){
        return this.questionService.createQuestion(questionList, "UPDATE");
    }

    @GetMapping("get")
    public ResponseEntity<List<QuestionWrapper>> getQuestionById(@RequestParam List<Integer> questionIdList){
        return this.questionService.getQuestionById(questionIdList);
    }

    @GetMapping("getRandomQuestions")
    public ResponseEntity<List<Integer>> generateRandomQuestionsforQuiz(@RequestParam String category, @RequestParam int noOfQues ){
        return this.questionService.generateQuestionsforQuiz(category, noOfQues);
    }

    @PostMapping("getScoreForUserResponse")
    public ResponseEntity<Integer> checkUserResponse(@RequestBody List<UserResponse> userResponseList){
        return this.questionService.checkUserResponse(userResponseList);
    }

}
