package com.arun.question_service.dao;

import com.arun.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    public List<Question> findByCategory(String category);

    @Query(value="SELECT * FROM QUESTION WHERE CATEGORY =:category ORDER BY RAND() LIMIT :noOfQuestions", nativeQuery = true)
    public List<Question> findRandomQuestionsByCategory(String category, int noOfQuestions);

    @Query(value="SELECT ID FROM QUESTION WHERE CATEGORY =:category ORDER BY RAND() LIMIT :noOfQuestions", nativeQuery = true)
    public List<Integer> findRandomQuestionIdsByCategory(String category, int noOfQuestions);
}
