package com.arun.question_service.service;

import com.arun.question_service.dao.QuestionDao;
import com.arun.question_service.model.Question;
import com.arun.question_service.model.QuestionWrapper;
import com.arun.question_service.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private QuestionDao questionDao = null;

    QuestionService(QuestionDao questionDao){
        this.questionDao = questionDao;
    }

    public ResponseEntity<List<Question>> getAllQuestions(){
        ResponseEntity<List<Question>> responseEntity = null;
        try{
            return new ResponseEntity<>(this.questionDao.findAll(), HttpStatus.OK);
        }
       catch(Exception ex){
           System.out.println(ex.getMessage());
           ex.printStackTrace();
       }
        return new ResponseEntity<>(new ArrayList<Question>(), HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category){
        try{
            return new ResponseEntity<>(this.questionDao.findByCategory(category), HttpStatus.OK);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<Question>(), HttpStatus.OK);
    }

    public ResponseEntity<String> createQuestion(List<Question> questionList, String saveOrUpdate) {
        String responseString = null;
        ResponseEntity<String> responseEntity = null;
        try{
            this.questionDao.saveAll(questionList);
            if (questionList.size() == 1 && saveOrUpdate.equals("SAVE")) {
                responseString = "New Question is added";
            } else if (questionList.size() == 1 && saveOrUpdate.equals("UPDATE")) {
                responseString = "Question is updated";
            } else if (questionList.size() > 1 && saveOrUpdate.equals("SAVE")) {
                responseString = "Questions are added";
            } else if (questionList.size() > 1 && saveOrUpdate.equals("UPDATE")) {
                responseString = "Questions is updated";
            }
            responseEntity = new ResponseEntity<>(responseString, HttpStatus.CREATED);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            responseString = "Failed to add new question";
            responseEntity = new ResponseEntity<>(responseString, HttpStatus.PRECONDITION_FAILED);
        }
        return responseEntity;
    }

    private List<QuestionWrapper> convertToWrapperObj(List<Question> questionList){
        List<QuestionWrapper> questionWrapperList = new ArrayList<>();
        for(Question question : questionList){
            questionWrapperList.add(new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4()));
        }
        return questionWrapperList;
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionById(List<Integer> questionIdList){
        List<Question> questionList = this.questionDao.findAllById(questionIdList);
        List<QuestionWrapper> questionWrapperList = convertToWrapperObj(questionList);
        return new ResponseEntity<>(questionWrapperList, HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> generateQuestionsforQuiz(String category, int noOfQuestions){
        List<Integer> questionIdList = this.questionDao.findRandomQuestionIdsByCategory(category, noOfQuestions);
        return new ResponseEntity<>(questionIdList, HttpStatus.OK);
    }

    public ResponseEntity<Integer> checkUserResponse(List<UserResponse> userResponseList) {

        List<Integer> questionIdList = userResponseList.stream()
                .map(UserResponse::getQuestionId)
                .collect(Collectors.toList());

        List<Question> questionList = this.questionDao.findAllById(questionIdList);

        int matchCount = 0;

        for(UserResponse userResponse : userResponseList){
            int match = (int) questionList.stream()
                    .filter( question -> question.getId() == userResponse.getQuestionId() && question.getRightAnswer().equals(userResponse.getResponse()))
                    .count();
            matchCount  += match == 1 ? 1 : 0;
        }
        System.out.println("matchCount: "+matchCount);
        return new ResponseEntity<>(matchCount, HttpStatus.OK);
    }
}
