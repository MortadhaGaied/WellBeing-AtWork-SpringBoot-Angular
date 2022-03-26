package com.wellbeignatwork.backend.repository;

import com.wellbeignatwork.backend.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IntCommentRepo extends CrudRepository<Comment,Integer> {
}
