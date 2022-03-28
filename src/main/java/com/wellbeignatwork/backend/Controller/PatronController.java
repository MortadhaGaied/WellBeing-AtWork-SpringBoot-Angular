package com.wellbeignatwork.backend.Controller;

import com.wellbeignatwork.backend.ServiceImp.IQuizServices;
import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatronController {
	@Autowired
	private IQuizServices quizS;
	
	
	@PostMapping("/addQuiz")
	public void addQuiz(@RequestBody Quiz quiz) {
		this.quizS.addQuiz(quiz);
	}
	
	@PutMapping("/editQuiz/{id}")
	public void editQuiz(@RequestBody Quiz quiz,@PathVariable Long id) {
		this.quizS.updateQuiz(quiz,id);
	}
	
	@PostMapping("/addQuestion")
	public void addQuestion(@RequestBody Question ques) {
		this.quizS.addQuestion(ques);
		
	}
	
	@PostMapping("/editQuestion")
	public void editQuestion(@RequestBody Question ques) {
		this.quizS.updateQues(ques);
	}
	
	@PostMapping("/deleteQuestion/{quizID}/{id}")
	public void deleteQuestion(@PathVariable Long quizID, @PathVariable Long id) {
		this.quizS.deleteQues(id, quizID);
	}
	
	@GetMapping("/deleteQuiz/{id}")
	public void deleteQuiz(@PathVariable Long id) {
		this.quizS.deleteQuiz(id);
	}
	
}
