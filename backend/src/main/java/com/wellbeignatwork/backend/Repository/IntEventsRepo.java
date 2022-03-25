package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.Entity.Events;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntEventsRepo extends CrudRepository<Events,Integer> {
}
