package com.wellbeignatwork.backend.entity.Collaboration;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionTheme {

    private List<QuestionCollaboration> questions;

    public List<QuestionCollaboration> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionCollaboration> questions) {

        this.questions = questions;

    }
}
