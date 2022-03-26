package com.wellbeignatwork.backend.repository;

import com.wellbeignatwork.backend.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IntUserRepo extends CrudRepository<User,Integer> {
    Iterable<User> findAll(Sort pointFidelite);
}
