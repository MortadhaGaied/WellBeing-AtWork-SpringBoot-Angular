package com.wellbeignatwork.backend.repository.User;

import com.wellbeignatwork.backend.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    List<User> findUsersByMessagesIsNull();
    User findUserByDisplayNameAndPassword(String displayName, String password);
    List<User> findAll(Sort pointFidelite);


    @Query(value= "select test.title , count(a.id) from Test test join test.employee a group by test")
    List<Object[]> getNbrEmployeeByTest();


    @Query(value = "select test.employee from  Test test  where test.idTest = :id")
    List<User> getEmployeeByTest(@Param("id") Integer idTest );


    @Query(value="select user from User user where user.profession=1")
    List<User> getEmployee();


    //MaxScoreInTest
    @Query(value = "select Max(result.totalCorrect) from User user join user.TestEmployee employee join employee.quizzes quiz join quiz.results result  where user.profession='EMPLOYEE'")
    Integer MaxScoreInTest();


    @Query(value = "select result.sUser,SUM (result.totalCorrect) from Result result join result.quiz quiz join quiz.test test where test.idTest=:id group by result.sUser order by SUM (result.totalCorrect) desc")
    List<Object> getEmployeeWithScoreQuiz(@Param("id") Integer id);


    @Query(value = "select result.sUser from Result result join result.quiz quiz join quiz.test test where test.idTest=:id group by result.sUser order by SUM (result.totalCorrect) desc")
    List<User> getEmployeeWithScoreForGifts(@Param("id") Integer id);

    @Query(value = "select result.sUser from Test test join test.quizzes quiz join quiz.results result where" +
            " result.totalCorrect=(select Max(r.totalCorrect) from User u join u.TestEmployee f join f.quizzes q join q.results r " +
            " where u.profession='LEARNER') and test.idTest=:id")
    User EmployeewithMaxScoreInTest(@Param("id") Integer id);
}
