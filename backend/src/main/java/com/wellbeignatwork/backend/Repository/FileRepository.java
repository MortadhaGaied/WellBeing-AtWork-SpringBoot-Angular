package com.wellbeignatwork.backend.repository;

import com.wellbeignatwork.backend.entity.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<File,Integer> {
}
