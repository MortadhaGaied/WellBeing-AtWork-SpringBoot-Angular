package com.wellbeignatwork.backend.ServiceImp;

import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Quiz;

import java.util.List;

public interface IQuestionService {
	 public List<Question> getQuestions(int id);
	 public void addQuestion(String question, String answer, Long marks, Quiz quiz, String optA, String optB, String optC, String optD);
	 public void saveQuestions();
}
