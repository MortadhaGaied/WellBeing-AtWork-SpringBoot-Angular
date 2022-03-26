package com.wellbeignatwork.backend.repository;

import com.wellbeignatwork.backend.entity.Advice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntAdviceRepo extends CrudRepository<Advice,Integer> {
}
