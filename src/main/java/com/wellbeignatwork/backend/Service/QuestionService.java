package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.ServiceImp.IQuestionService;
import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Quiz;

import java.util.List;

public class QuestionService implements IQuestionService {

	QuizService quizS = new QuizService();
	@Override
	public List<Question> getQuestions(Long id) {

		return null;
	}

	@Override
	public void addQuestion(String question, String answer, Long marks, Quiz quiz, String optA, String optB, String optC,
							String optD) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveQuestions() {
		// TODO Auto-generated method stub

	}

}
