package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.Entity.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<File,Integer> {
}
