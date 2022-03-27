package com.wellbeignatwork.backend.ServiceImp;

import com.wellbeignatwork.backend.model.Question;

import java.util.List;

public interface QuestionService {
	List<Question> getAllQuestions();
	Question saveQuestion(Question question);
	Question findQuestionByquestionId(Long id);
	void deleteQuestionByquestionId(Long id);
}
