package com.wellbeignatwork.backend.Service;


import com.wellbeignatwork.backend.Repository.QuizRepository;
import com.wellbeignatwork.backend.ServiceImp.QuizService;
import com.wellbeignatwork.backend.model.Quiz;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
	
	
	private QuizRepository quizRepository;
	
	
	public QuizServiceImpl(QuizRepository quizRepository) {
		super();
		this.quizRepository = quizRepository;
	}


	public Quiz saveQuiz(Quiz quiz) {
		return quizRepository.save(quiz);
	}
	
}
