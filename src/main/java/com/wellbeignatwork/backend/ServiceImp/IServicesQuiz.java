package com.wellbeignatwork.backend.ServiceImp;



import com.wellbeignatwork.backend.model.*;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IServicesQuiz {


    void addQuiz(QuizCourses quiz, Integer idF);
    void addQuestionAndAsigntoQuiz(QuestionCourses question, Integer idQuiz);
   // void addAnswerAndAsigntoQuestion(Answer answer,Integer idQuestion,Integer idQuiz);

    List<QuestionCourses> getQuizQuestion(Integer idQuiz);
    List<QuestionCourses> getQuestions();
    int getResult(QuestionForm qForm);

    Integer saveScore(Result result, Long idUser, Integer idQuiz);
    User ApprenentwithMaxScoreInFormation(Integer id);

    Object ApprenentwithMaxScore(@Param("id") Integer id);
    Integer MaxScoreInFormation();

    List<Object> getApprenantWithScoreQuiz(@Param("id") Integer id);

    List<Result> getTopScore();

    Integer getScore( Long idU);

    List<QuizCourses> getQuizByFormation(Integer idF);

    void DeleteQuiz(Integer idQ);

    User ApprenentwithMaxScoreQuiz(Integer id);


    void giftsToUserMaxScoreInCourses();





}
