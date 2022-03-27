package com.wellbeignatwork.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import com.wellbeignatwork.backend.entity.User;
@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Iterable<User> findAll(Sort pointFidelite);
}
