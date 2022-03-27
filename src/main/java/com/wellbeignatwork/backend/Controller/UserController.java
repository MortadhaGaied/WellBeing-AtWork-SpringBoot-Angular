package com.wellbeignatwork.backend.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.Repository.ThemeRepository;
import com.wellbeignatwork.backend.ServiceImp.QuestionService;
import com.wellbeignatwork.backend.ServiceImp.UserService;
import com.wellbeignatwork.backend.model.Theme;
import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private QuestionService questionService;
	@Autowired
	private ThemeRepository themeRepository ;


	@PostMapping("/moderateur/question/add")
	//@PreAuthorize("hasRole('MODERATOR')")
	@ResponseBody
	public String addQuestion(@RequestBody Questions questions, Model model) {

		Question question = new Question();
		List<Theme> themes = new ArrayList<>();
		Theme optionOne = new Theme(questions.getOptionOne());
		Theme optionTwo = new Theme(questions.getOptionTwo());
		Theme optionThree = new Theme(questions.getOptionThree());
		Theme optionFour = new Theme(questions.getOptionFour());
		themes.add(optionOne);
		themes.add(optionTwo);
		themes.add(optionThree);
		themes.add(optionFour);
		question.setTitle(questions.getQuestionTitle());


		switch (questions.getCorrectAnswer()) {
			case "1":
				question.setThemeChosen(questions.getOptionOne());
				break;
			case "2":
				question.setThemeChosen(questions.getOptionTwo());
				break;
			case "3":
				question.setThemeChosen(questions.getOptionThree());
				break;
			case "4":
				question.setThemeChosen(questions.getOptionFour());
				break;
		}

		for(Theme theme: themes) {
			theme.setQuestion(question);
		}
		question.setThemes(themes);
		questionService.saveQuestion(question);
		return "redirect:/teacher/questions/list";
	}

	@GetMapping("/moderator/question/{id}")
	//@PreAuthorize("hasRole('MODERATOR')")
	@ResponseBody
	public String showModifyQuestionForm(@PathVariable Long id, Model model) {
		Question existingQuestion = questionService.findQuestionByquestionId(id);
		Questions questions = new Questions();
		questions.setQuestionTitle(existingQuestion.getTitle());
		List<Theme> themes = existingQuestion.getThemes();
		questions.setOptionOne(themes.get(0).getOptionText());
		questions.setOptionTwo(themes.get(1).getOptionText());
		questions.setOptionThree(themes.get(2).getOptionText());
		questions.setOptionFour(themes.get(3).getOptionText());
		questions.setCorrectAnswer(existingQuestion.getThemeChosen());

		questions.setQuestionId(existingQuestion.getQuestionId());
		model.addAttribute("questionsDto", questions);
		return "teacher-questions-edit";
	}

	@PostMapping("/moderator/question/{id}")
	//@PreAuthorize("hasRole('MODERATOR')")
	@ResponseBody
	public String updateQuestionDetails(@PathVariable Long id, @RequestBody Questions questions, Model model) {

		Question existingQuestion = questionService.findQuestionByquestionId(id);
		List<Theme> existingThemes = existingQuestion.getThemes();
		existingQuestion.setTitle(questions.getQuestionTitle());

		existingThemes.get(0).setOptionText(questions.getOptionOne());
		existingThemes.get(1).setOptionText(questions.getOptionTwo());
		existingThemes.get(2).setOptionText(questions.getOptionThree());
		existingThemes.get(3).setOptionText(questions.getOptionFour());
		switch (questions.getCorrectAnswer()) {
			case "1":
				existingQuestion.setThemeChosen(questions.getOptionOne());
				break;
			case "2":
				existingQuestion.setThemeChosen(questions.getOptionTwo());
				break;
			case "3":
				existingQuestion.setThemeChosen(questions.getOptionThree());
				break;
			case "4":
				existingQuestion.setThemeChosen(questions.getOptionFour());
				break;
		}

		questionService.saveQuestion(existingQuestion);
		return "redirect:/teacher/questions/list";

	}

	@GetMapping("/moderator/questions/list")
	//@PreAuthorize("hasRole('MODERATOR')")
	@ResponseBody
	@JsonIgnore
	public List<Question> showAllQuestionsPage() {
		List<Question> Questions = questionService.getAllQuestions();




		return Questions;
	}


	@GetMapping("/moderator/question/delete/{id}")
	//@PreAuthorize("hasRole('MODERATOR')")
	@ResponseBody
	public String deleteQuestion(@PathVariable Long id, Model model) {
		Question question = questionService.findQuestionByquestionId(id);
		List<Theme> themes = question.getThemes();
		for(Theme theme: themes) {
			themeRepository.delete(theme);
		}
		questionService.deleteQuestionByquestionId(id);
		return "redirect:/teacher/questions/list";
	}

}