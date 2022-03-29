package com.wellbeignatwork.backend.repository.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.QuestionCollaboration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionRepo extends CrudRepository<QuestionCollaboration,Integer> {
}
