package com.wellbeignatwork.backend.repository.Event;

import com.wellbeignatwork.backend.entity.Event.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends CrudRepository<User, Long> {

}
