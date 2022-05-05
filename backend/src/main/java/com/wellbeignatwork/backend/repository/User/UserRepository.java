package com.wellbeignatwork.backend.repository.User;

import com.wellbeignatwork.backend.entity.User.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Userr, Long> {

    Userr findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Userr a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    List<Userr> findUsersByMessagesIsNull();
    Userr findUserByDisplayNameAndPassword(String displayName, String password);
    List<Userr> findAll(Sort pointFidelite);

}
