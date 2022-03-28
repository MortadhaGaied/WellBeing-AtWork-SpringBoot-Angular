package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.Repository.QuizRepo;
import com.wellbeignatwork.backend.ServiceImp.IQuizServices;
import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService implements IQuizServices {
	@Autowired
	QuizRepo quizRepo;
	public static List<Quiz> quizes = new ArrayList<Quiz>();
	@Override
	public List<Quiz> getQuizes() {
		return quizes;
	}

	@Override
	public Quiz addQuiz(Quiz quiz) {
		Quiz q = quizRepo.save(quiz);

		return q;
	}

	@Override
	public void deleteQuiz(Long id) {
		quizes.remove(id);
	}

	@Override
	public Quiz updateQuiz(Quiz q,Long id) {
		Quiz quiz =quizRepo.findById(id).orElse(null);
		quiz.setDescription(q.getDescription());
		quiz.setTitle(q.getTitle());
		quiz.setQuestions(q.getQuestions());
		return quizRepo.save(quiz);
	}

	@Override
	public List<Question> getQuestions(Long idQuiz) {
		Quiz q = quizRepo.findById(idQuiz).orElse(null);
		return q.getQuestions();
	}

	@Override
	public void addQuestion(Question ques) {
		//int quizID = ques.getQuiz().getId();
		//quizes.get(quizID).getQuestions().add(ques);
		
	}

	@Override
	public void deleteQues(Long id, Long quizID) {
		//quizes.get(quizID).getQuestions().remove(id);
	}

	@Override
	public void updateQues(Question ques) {
		/*int quizID = ques.getQuiz().getId();
		Quiz quiz = quizes.get(quizID);
		quiz.getQuestions().get(ques.getId()).setQuestion(ques.getQuestion());
		quiz.getQuestions().get(ques.getId()).setAnswer(ques.getAnswer());
		quiz.getQuestions().get(ques.getId()).setOptionA(ques.getOptionA());
		quiz.getQuestions().get(ques.getId()).setOptionB(ques.getOptionB());
		quiz.getQuestions().get(ques.getId()).setOptionC(ques.getOptionC());
		quiz.getQuestions().get(ques.getId()).setOptionD(ques.getOptionD());*/
	}

}
