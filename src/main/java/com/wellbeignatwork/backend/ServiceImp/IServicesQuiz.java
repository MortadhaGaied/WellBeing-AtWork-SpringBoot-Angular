package com.wellbeignatwork.backend.ServiceImp;


import com.wellbeignatwork.backend.model.*;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IServicesQuiz {


    void addQuiz(QuizTheme quiz, Integer idF);
    void addQuestionAndAsigntoQuiz(Question question, Integer idQuiz);
   // void addAnswerAndAsigntoQuestion(Answer answer,Integer idQuestion,Integer idQuiz);

    List<Question> getQuizQuestion(Integer idQuiz);
    List<Question> getQuestions();
    int getResult(QuestionTheme qForm);

    Integer saveScore(Result result, Long idUser, Integer idQuiz);



    Integer MaxScoreInTest();

    List<Object> EmployeeWithScoreQuiz(@Param("id") Integer id);

    List<Object> getEmployeeWithScoreQuiz(Integer id);

    List<Result> getTopScore();

    Integer getScore( Long idU);



    List<QuizTheme> getQuizByTest(Integer IdTest);

    void DeleteQuiz(Integer idQ);

    User TestwithMaxScoreQuiz(Integer id);


    User EmployeewithMaxScoreQuiz(Integer id);

    void giftsToUserMaxScoreInCourses();





}
