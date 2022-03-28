package com.wellbeignatwork.backend.ServiceImp;


import com.wellbeignatwork.backend.model.*;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IServicesQuiz {


    void addQuiz(QuizCourses quiz, Integer idF);
    void addQuestionAndAsigntoQuiz(Question question, Integer idQuiz);
   // void addAnswerAndAsigntoQuestion(Answer answer,Integer idQuestion,Integer idQuiz);

    List<Question> getQuizQuestion(Integer idQuiz);
    List<Question> getQuestions();
    int getResult(QuestionTheme qForm);

    Integer saveScore(Result result, Long idUser, Integer idQuiz);

    User EmployeewithMaxScoreInTest(Integer idUser);

    Object EmployeewithMaxScore(@Param("id") Integer id);
    Integer MaxScoreInTest();

    List<Object> getApprenantWithScoreQuiz(@Param("id") Integer id);

    List<Result> getTopScore();

    Integer getScore( Long idU);

    List<QuizCourses> getQuizByTest(Integer idF);

    void DeleteQuiz(Integer idQ);

    User TestwithMaxScoreQuiz(Integer id);


    void giftsToUserMaxScoreInCourses();





}
