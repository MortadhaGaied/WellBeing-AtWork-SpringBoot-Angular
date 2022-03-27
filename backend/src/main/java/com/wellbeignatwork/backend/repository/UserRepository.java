package com.wellbeignatwork.backend.repository;

import com.wellbeignatwork.backend.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Iterable<User> findAll(Sort pointFidelite);
}
