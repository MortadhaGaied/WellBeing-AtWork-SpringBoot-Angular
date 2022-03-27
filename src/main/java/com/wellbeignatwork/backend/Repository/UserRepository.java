package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByName(String username);
    boolean existsByEmail(String email);

    @Query(value = "select r.sUser from Result r join r.quiz q join q.formation f where f.idFormation=:id group by r.sUser order by SUM (r.totalCorrect) desc")
    List<User> getUserWithScoreForGifts(@Param("id") Integer id);

    @Query(value = "select r.sUser from Formation f join f.quizzes q join q.results r where" +
            " r.totalCorrect=(select Max(r.totalCorrect) from User u join u.formationE f join f.quizzes q join q.results r " +
            " where u.role='ROLE_Employee') and f.idFormation=:id")
    User EmployeeWithMaxScoreInFormation(@Param("id") Integer id);
    @Query(value = "select r.sUser,SUM (r.totalCorrect) from Result r join r.quiz q join q.formation f where f.idFormation=:id group by r.sUser order by SUM (r.totalCorrect) desc")
    List<Object> getEmployeeWithScoreQuiz(@Param("id") Integer id);

    @Query(value = "select Max(r.totalCorrect) from User u join u.formationE f join f.quizzes q join q.results r  where u.role='ROLE_Employee'")
    Integer MaxScoreInFormation();

}