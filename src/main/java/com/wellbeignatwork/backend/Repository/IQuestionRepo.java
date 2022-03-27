package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.model.QuestionCourses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionRepo extends CrudRepository<QuestionCourses,Integer> {
}
