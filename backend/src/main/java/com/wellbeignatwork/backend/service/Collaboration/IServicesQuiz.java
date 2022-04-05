package com.wellbeignatwork.backend.service.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.*;
import com.wellbeignatwork.backend.entity.User.User;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IServicesQuiz {


    void addQuiz(QuizTheme quiz, Integer idF);
    void addQuestionAndAsigntoQuiz(QuestionCollaboration question, Integer idQuiz);
   // void addAnswerAndAsigntoQuestion(Answer answer,Integer idQuestion,Integer idQuiz);

    List<QuestionCollaboration> getQuizQuestion(Integer idQuiz);
    List<QuestionCollaboration> getQuestions();
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

    void giftsToUserMaxScoreInTest();

}
