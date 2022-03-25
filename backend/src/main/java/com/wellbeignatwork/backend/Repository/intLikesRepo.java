package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.Entity.Likes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface intLikesRepo extends CrudRepository<Likes,Integer> {
}
