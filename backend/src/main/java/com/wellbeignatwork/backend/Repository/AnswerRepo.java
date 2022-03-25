package com.wellbeignatwork.backend.Repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.pidev.spring.version0backend.Entity.Answer;
import tn.pidev.spring.version0backend.Entity.Sentiment;

@Repository
public interface AnswerRepo extends CrudRepository<Answer,Integer> {

    @Query("select count (*) FROM Answer a where a.Sentiment = :status")
    int nbreByStatus(@Param("status") Sentiment status);





}
