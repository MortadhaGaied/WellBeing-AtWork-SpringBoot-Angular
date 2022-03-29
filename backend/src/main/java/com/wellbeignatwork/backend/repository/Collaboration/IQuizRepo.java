package com.wellbeignatwork.backend.repository.Collaboration;



import com.wellbeignatwork.backend.entity.Collaboration.QuestionCollaboration;
import com.wellbeignatwork.backend.entity.Collaboration.QuizTheme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuizRepo extends CrudRepository<QuizTheme,Integer> {

    @Query(value = "select  a from QuizTheme q join q.question a where q.idQuiz=:id")
    List<QuestionCollaboration> getQuizQuestion(@Param("id") Integer idQ);


    @Query(value = "select  q from QuizTheme q join q.test f where f.idTest=:id")
    List<QuizTheme> getQuizByTest(@Param("id") Integer IdTest);


}
