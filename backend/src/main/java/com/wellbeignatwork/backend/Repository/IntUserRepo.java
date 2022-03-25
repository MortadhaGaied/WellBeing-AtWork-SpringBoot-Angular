package com.wellbeignatwork.backend.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.pidev.spring.version0backend.Entity.User;

@Repository
public interface IntUserRepo extends CrudRepository<User,Integer> {
    Iterable<User> findAll(Sort pointFidelite);
}
