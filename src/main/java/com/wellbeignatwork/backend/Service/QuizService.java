package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.ServiceImp.IQuizServices;
import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService implements IQuizServices {

	public static List<Quiz> quizes = new ArrayList<Quiz>();
	@Override
	public List<Quiz> getQuizes() {
		return quizes;
	}

	@Override
	public Quiz addQuiz(String title, String description) {
		Quiz quiz = new Quiz(title, description);
		quizes.add(quiz);
		return quiz;
	}

	@Override
	public void deleteQuiz(int id) {
		quizes.remove(id);
	}

	@Override
	public void updateQuiz(Quiz quiz) {
		int id = quiz.getId();
		quizes.get(id).setTitle(quiz.getTitle());
		quizes.get(id).setDescription(quiz.getDescription());
	}

	@Override
	public List<Question> getQuestions(int id) {
		return quizes.get(id).getQuestions();
	}

	@Override
	public void addQuestion(Question ques) {
		int quizID = ques.getQuiz().getId();
		quizes.get(quizID).getQuestions().add(ques);
		
	}

	@Override
	public void deleteQues(int id, int quizID) {
		quizes.get(quizID).getQuestions().remove(id);
	}

	@Override
	public void updateQues(Question ques) {
		int quizID = ques.getQuiz().getId();
		Quiz quiz = quizes.get(quizID);
		quiz.getQuestions().get(ques.getId()).setQuestion(ques.getQuestion());
		quiz.getQuestions().get(ques.getId()).setAnswer(ques.getAnswer());
		quiz.getQuestions().get(ques.getId()).setOptionA(ques.getOptionA());
		quiz.getQuestions().get(ques.getId()).setOptionB(ques.getOptionB());
		quiz.getQuestions().get(ques.getId()).setOptionC(ques.getOptionC());
		quiz.getQuestions().get(ques.getId()).setOptionD(ques.getOptionD());
	}

}
