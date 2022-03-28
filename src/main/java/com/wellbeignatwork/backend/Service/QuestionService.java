package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.Repository.QuestionRepo;
import com.wellbeignatwork.backend.ServiceImp.IQuestionService;
import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QuestionService implements IQuestionService {
	@Autowired
	QuestionRepo questionRepo;
	QuizService quizS = new QuizService();
	@Override
	public List<Question> getQuestions(Long id) {

		return null;
	}

	@Override
	public void addQuestion(Question question) {
		questionRepo.save(question);
	}

	@Override
	public void saveQuestions() {
		// TODO Auto-generated method stub

	}

}
