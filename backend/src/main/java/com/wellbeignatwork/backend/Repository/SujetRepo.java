package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.Entity.Sujet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SujetRepo extends CrudRepository<Sujet,Integer> {


}
