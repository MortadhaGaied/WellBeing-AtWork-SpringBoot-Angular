package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionRepo extends CrudRepository<Question,Integer> {
}
