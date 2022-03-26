package com.wellbeignatwork.backend.Repository;


import com.wellbeignatwork.backend.model.Publicity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublicity extends JpaRepository<Publicity, Long> {


}
