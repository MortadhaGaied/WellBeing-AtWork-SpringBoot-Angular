package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
    Boolean existsByName(String name);
    void deleteByName(String name);
}
