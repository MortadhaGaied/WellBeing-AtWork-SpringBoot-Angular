package com.wellbeignatwork.backend.repository;
import com.wellbeignatwork.backend.entity.Event;

import com.wellbeignatwork.backend.entity.FeedBack;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRep extends CrudRepository<FeedBack,Long> {
    @Query("SELECT AVG(f.rate) FROM FeedBack f WHERE f.event = ?1 AND f.rate IS NOT NULL")
    Float getAverageRateEvent(Event event);
}
