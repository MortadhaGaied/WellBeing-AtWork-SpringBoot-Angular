package com.wellbeignatwork.backend.repository;

import com.wellbeignatwork.backend.entity.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
@Query("select e from Event e where e.eventDate between :startDate  and :endDate ")
    public List<Event> reminder(Date startDate, Date endDate);
/*@Query("SELECT COUNT(e) as n, e.eventTags as tag FROM Event e GROUP BY e.eventTags ")
    public List<ITagsCount> tags();*/
}
