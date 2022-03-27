package com.wellbeignatwork.backend.repository.Forum;

import com.wellbeignatwork.backend.entity.Forum.Reaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends CrudRepository<Reaction,Integer> {
    @Query("SELECT count(r) FROM Reaction r where r.idUser=:idUser")
    public int NbrReactByUser(Long idUser);
}
