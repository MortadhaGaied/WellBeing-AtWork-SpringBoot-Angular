package com.wellbeignatwork.backend.Repository;


import com.wellbeignatwork.backend.model.Domain;
import com.wellbeignatwork.backend.model.Test;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ITestRepo extends CrudRepository<Test,Integer> {


    @Query(value="select count(a.id) from Test f join f.employee a where f.title=:titre")
    Integer getNbrEmployeeByTest(@Param("titre") String titre);

    @Query(value="select count(employee.id) from Test test join test.employee employee where test.idTest=:id")
    Integer getNbrEmployeeByTestId(@Param("id") Integer id);

    @Query(value = "select count(test.idTest) from Test test join test.employee employee where employee.id=:id and test.domain=:domain")
    Integer getNbrEmployeeByTest(@Param("id") int idUser, @Param("domain") Domain domain);


    @Query(value = "select f from Test f where concat(f.title,f.level,f.domain,f.frais,f.nbrHeures,f.nbrMaxParticipant) like %?1% group by f order by sum(f.likes-f.dislikes) desc")
    List<Test> rech(String keyword);

    @Query(value = "select test from Test test join test.employee employee where employee.id=:id")
    List<Test> listEmployeeParTest(@Param("id") Long idApp);


    @Query(value = "select count(f.idTest) from Test f join f.formateur fr where f.start>=:dateD and f.end<=:dateF and fr.id=:id")
    Integer nbrCoursesParFormateur(@Param("id") Long idF, @Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);

    @Query(value="select count(employee.id) from Test test join test.employee employee where test.title=:titre")
    Integer getNbrEmployeeByTestt(@Param("titre") String titre);

}
