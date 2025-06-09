package com.arun.question_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionWrapper {
    int questionId;
    String questionTitle;
    String option1;
    String option2;
    String option3;
    String option4;
}
