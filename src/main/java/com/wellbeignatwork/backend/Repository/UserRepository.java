package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("select u from User u where u.profession='Doctor' order by u.Score desc ")
    List<User> classementDoctor();

    /////////////////////////////////   Mahdi User methode       /////////////////////////////
    @Query(value= "select f.title , count(a.id) from Test f join f.apprenant a group by f")
    List<Object[]> getNbrApprenantByTest();


    @Query(value="select f.apprenant from  Test f  where f.idTest = :id")
    List<User> getRevenueByFormation(@Param("id") Integer idTest);


    @Query(value = "select f.apprenant from  Test f  where f.idTest = :id")
    List<User> getApprenantByFormation(@Param("id") Integer idF );


    @Query(value = "select test.formateur from  Test test  where test.idTest = :id")
    List<User> getFormateurByTest(@Param("id") Integer idTest );


    @Query(value= "select SUM(f.nbrHeures*f.formateur.tarifHoraire) from Test f where f.formateur.id=:id and f.start>=:dateD and f.end<=:dateF")
    Integer getFormateurRemunerationByDate(@Param("id") Integer idFormateur, @Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);


    @Query(value="select count(a.id) from Test f join f.apprenant a where f.title=:titre")
    Integer getNbrApprenantByFormation(@Param("titre") String titre );


    @Query(value="select f from User f where f.profession=0")
    List<User> getFormateur();



    @Query(value="select f from User f where f.profession=1")
    List<User> getApprenant();



    @Query(value = "select f.formateur from Test f  where f.formateur.tarifHoraire =(select Max(u.tarifHoraire) from User u where u.profession='FORMER')")
    User FormateurwithMaxHo();



    @Query(value = "select Max(r.totalCorrect) from User u join u.TestA f join f.quizzes q join q.results r  where u.profession='LEARNER'")
    Integer MaxScoreInFormation();


    @Query(value = "select r.sUser from Test f join f.quizzes q join q.results r where" +
            " r.totalCorrect=(select Max(r.totalCorrect) from User u join u.formationA f join f.quizzes q join q.results r " +
            " where u.profession='LEARNER') and f.idFormation=:id")
    User ApprenentwithMaxScoreInFormation(@Param("id") Integer id);

    @Query(value = "select r.sUser,SUM (r.totalCorrect) from Result r join r.quiz q join q.test f where f.idTest=:id group by r.sUser order by SUM (r.totalCorrect) desc")
    List<Object> getApprenantWithScoreQuiz(@Param("id") Integer id);



    @Query(value = "select r.sUser from Result r join r.quiz q join q.test f where f.idFormation=:id group by r.sUser order by SUM (r.totalCorrect) desc")
    List<User> getApprenantWithScoreForGifts(@Param("id") Integer id);

    //////////////////////////////             03/19/2022             ///////////////////////////////

}