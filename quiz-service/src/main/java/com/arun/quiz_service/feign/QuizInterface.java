package com.arun.quiz_service.feign;

import com.arun.quiz_service.model.QuestionWrapper;
import com.arun.quiz_service.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("question/get")
    public ResponseEntity<List<QuestionWrapper>> getQuestionById(@RequestParam List<Integer> questionIdList);

    @GetMapping("question/getRandomQuestions")
    public ResponseEntity<List<Integer>> generateRandomQuestionsforQuiz(@RequestParam String category, @RequestParam int noOfQues );

    @PostMapping("question/getScoreForUserResponse")
    public ResponseEntity<Integer> checkUserResponse(@RequestBody List<UserResponse> userResponseList);
}
