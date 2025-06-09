package com.arun.quiz_service.service;

import com.arun.quiz_service.dao.QuizDao;
import com.arun.quiz_service.feign.QuizInterface;
import com.arun.quiz_service.model.QuestionWrapper;
import com.arun.quiz_service.model.Quiz;
import com.arun.quiz_service.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<Map<Integer, List<QuestionWrapper>>> getQuizQuestions(){
        Map<Integer, List<QuestionWrapper>> questionWrapperMap = new HashMap<>();
        List<Quiz> quizList = this.quizDao.findAll();
        for(Quiz quiz : quizList){
            ResponseEntity<List<QuestionWrapper>> response = this.quizInterface.getQuestionById(quiz.getQuestionList());
            List<QuestionWrapper> questionWrapperList = response.getBody();
            questionWrapperMap.put(quiz.getId(), questionWrapperList);
        }
        return new ResponseEntity<>( questionWrapperMap, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(int id){
        Optional<Quiz> quizObj = this.quizDao.findById(id);
        List<Integer> questionList = quizObj.isPresent() ? quizObj.get().getQuestionList() :  new ArrayList<>();
        return this.quizInterface.getQuestionById(questionList);
    }

    public ResponseEntity<String> createQuiz(String category, String title, int noOfQuestions) {
        List<Integer> questionIdList = this.quizInterface.generateRandomQuestionsforQuiz(category, noOfQuestions).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionList(questionIdList);
        this.quizDao.save(quiz);
        return new ResponseEntity<>("Quiz is created", HttpStatus.CREATED);
    }

    public ResponseEntity<Integer> checkUserResponse(int quizId, List<UserResponse> userResponseList) {
        return this.quizInterface.checkUserResponse(userResponseList);
    }
}
