package com.wellbeignatwork.backend.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.pidev.spring.version0backend.Entity.Likes;

@Repository
public interface intLikesRepo extends CrudRepository<Likes,Integer> {
}
