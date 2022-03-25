package com.wellbeignatwork.backend.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.pidev.spring.version0backend.Entity.Comment;

@Repository
public interface IntCommentRepo extends CrudRepository<Comment,Integer> {
}
