package com.wellbeignatwork.backend.repository.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.Survey;
import com.wellbeignatwork.backend.entity.Evaluation.VoteIdea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepo extends CrudRepository<Survey,Integer> {

}
