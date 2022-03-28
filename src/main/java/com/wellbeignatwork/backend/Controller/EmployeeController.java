package com.wellbeignatwork.backend.Controller;

import com.wellbeignatwork.backend.Service.QuizService;
import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Quiz;
import com.wellbeignatwork.backend.model.Result;
import com.wellbeignatwork.backend.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class EmployeeController {
	
	Quiz quiz;
	int totalMarks;
	int currQuesId;
	ArrayList<Result> results = new ArrayList<Result>();
	User user = LoginController.user;
	
	@GetMapping("/startQuiz/{id}")
	public void startQuiz(@PathVariable Long quizID) {
		/*quiz = QuizService.quizes.get(quizID);
		totalMarks = 0;
		currQuesId = 0;*/
	}
	
	@PostMapping("/questions/{id}")
	public void submitAnswer(@PathVariable Long id, @RequestBody String Answer) {
		/*Question ques = quiz.getQuestions().get(id);
		if(ques.getAnswer()== Answer) {
			totalMarks+=ques.getMarks();
		}*/
	}
	
	@PostMapping("/submit")
	public void submitQuiz() {
		//Result rs = new Result(quiz.getId(), Math.toIntExact(user.getId()), totalMarks);
		//results.add(rs);
	}
	@GetMapping("/Results")
	public ArrayList<Result> Results() {
		return results;
	}
}
