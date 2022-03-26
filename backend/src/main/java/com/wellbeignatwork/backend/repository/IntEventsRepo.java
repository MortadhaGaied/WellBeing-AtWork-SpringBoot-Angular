package com.wellbeignatwork.backend.repository;

import com.wellbeignatwork.backend.entity.Events;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntEventsRepo extends CrudRepository<Events,Integer> {
}
