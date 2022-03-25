package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.Entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IntCommentRepo extends CrudRepository<Comment,Integer> {
}
