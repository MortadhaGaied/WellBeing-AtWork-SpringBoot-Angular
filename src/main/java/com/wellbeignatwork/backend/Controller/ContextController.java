package com.wellbeignatwork.backend.Controller;

import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.Quiz;
import com.wellbeignatwork.backend.model.Session;
import com.wellbeignatwork.backend.model.User;

import java.util.List;


public class ContextController {

	//public static Learner Learner;
	//public static Admin admin;
	//public static Former former;
	public static User user;
	public static Session session;
	public static Quiz quiz;
	public static List<Question> questions;

	public static User getUser() {
		return user;
	}
	public static void setUser(User user) {
		ContextController.user = user;
	}

	public static Session getSession() {
		return session;
	}
	public static void setSession(Session session) {
		ContextController.session = session;
	}
	public static Quiz getQuiz() {
		return quiz;
	}
	public static void setQuiz(Quiz quiz) {
		ContextController.quiz = quiz;
	}


}
