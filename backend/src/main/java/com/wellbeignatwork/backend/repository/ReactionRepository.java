package com.wellbeignatwork.backend.repository;

import com.wellbeignatwork.backend.entity.Reaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends CrudRepository<Reaction,Integer> {
}
