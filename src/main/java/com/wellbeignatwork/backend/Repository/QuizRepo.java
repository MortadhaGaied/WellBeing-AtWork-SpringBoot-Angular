package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepo extends JpaRepository<Quiz, Long> {

}
