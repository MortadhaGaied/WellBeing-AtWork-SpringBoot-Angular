package com.wellbeignatwork.backend.Repository;

import com.wellbeignatwork.backend.model.Search;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ISearchRepo extends CrudRepository<Search,Integer> {

    @Query(value = "select test.title from Test test where concat(test.title,test.level,test.domain,test.nbrHeures,test.nbrParticipant) like %?1% order by test.title")
    String keyWord(String keyword);

}
