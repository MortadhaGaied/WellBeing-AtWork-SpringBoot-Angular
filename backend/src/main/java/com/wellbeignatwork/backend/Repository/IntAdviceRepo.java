package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.Entity.Advice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntAdviceRepo extends CrudRepository<Advice,Integer> {
}
