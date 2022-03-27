package com.wellbeignatwork.backend.Repository;


import com.wellbeignatwork.backend.model.Domain;
import com.wellbeignatwork.backend.model.Formation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IFormationRepo extends CrudRepository<Formation,Integer> {

    @Query(value="select count(a.id) from Formation f join f.employee a where f.title=:titre")
    Integer getNbrEmployeeByFormation(@Param("titre") String titre);

    @Query(value="select count(a.id) from Formation f join f.employee a where f.idFormation=:id")
    Integer getNbrEmployeeByFormationId(@Param("id") Integer id);

    @Query(value = "select count(f.idFormation) from Formation f join f.employee a where a.id=:id and f.start>=:dateD and f.end<=:dateF and f.domain=:domain")
    Integer getNbrFormationByEmployee(@Param("id") Long idApp, @Param("domain") Domain domain, @Param("dateD") Date dateDebut, @Param("dateF") Date dateFin);


    @Query(value = "select f from Formation f where concat(f.title,f.level,f.domain,f.frais,f.nbrHeures,f.nbrMaxParticipant) like %?1% group by f order by sum(f.likes-f.dislikes) desc")
    List<Formation> rech(String keyword);

    @Query(value = "select f from Formation f join f.employee a where a.id=:id")
    List<Formation> listFormationParEmployee(@Param("id") Long idApp);

}
