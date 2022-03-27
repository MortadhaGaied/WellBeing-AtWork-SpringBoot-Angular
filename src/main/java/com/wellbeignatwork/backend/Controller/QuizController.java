package com.wellbeignatwork.backend.Controller;

import com.wellbeignatwork.backend.ServiceImp.QuestionService;
import com.wellbeignatwork.backend.ServiceImp.SessionService;
import com.wellbeignatwork.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


@RestController
@RequestMapping("/quiz")
public class QuizController {

	private QuestionService questionService;

	@Autowired
	private SessionService sessionService;


	public QuizController(QuestionService questionService) {
		super();
		this.questionService = questionService;
	}

	@GetMapping("/{subject}")
	@ResponseBody
	public String showQuiz(@PathVariable String subject, Model model) {
		Session session = new Session();
		session.setStart_time(new Timestamp(System.currentTimeMillis()));
		List<Question> questions = questionService.getAllQuestions();
		Quiz quiz = new Quiz( questions, session);
		session.setQuiz(quiz);
		session.setScore(0);
		ContextController.getUser().setSession(session);
		session.setUserId(ContextController.getUser().getId());
		session.setUserName(ContextController.getUser().getDisplayName());
		ContextController.setSession(session); //optional shared context
		sessionService.saveSession(session);
		ContextController.questions = questions;
		model.addAttribute("questions", questions);
		Answers answersDto = new Answers();
		model.addAttribute("answersDto", answersDto);
		model.addAttribute("User", ContextController.getUser());
		return "quiz";
	}


	@PostMapping("/{subject}/submit")
	@ResponseBody
	public String validateQuiz(@PathVariable String subject,
			@RequestBody Answers answers,
			Model model) {
		List<Question> questions = ContextController.questions;
		int correct=0;
		int opted=0;
		String msg= "";
		String[] submittedAnswers = answers.getAnswers();
		for(int i=0; i<questions.size(); i++) {
			if(submittedAnswers[i] != null) {
				opted++;
				if(submittedAnswers[i].equalsIgnoreCase(questions.get(i).getThemeChosen())) {
					correct++;
				}
			}
		}
		double score = Utils.calculateNegativeMarks(opted, correct);
		Session existingSession = sessionService.findSessionById(ContextController
				.getSession().getId());
		existingSession.setEnd_time(new Timestamp(System.currentTimeMillis()));
		existingSession.setScore(score);

		sessionService.saveSession(existingSession);
		if (score<2.0){
			msg="faible";
		}
		else if (score<7.0){
			msg="forte";
		}
		else {
			msg="ff";
		}

		System.out.println(msg);
		existingSession.setMsg(msg);

		model.addAttribute("msg", msg);
		model.addAttribute("total", "Out of " + questions.size());
		model.addAttribute("Learner", ContextController.getUser());
		return "quiz-results";
	}

}
