package com.wellbeignatwork.backend.repository.User;

import com.wellbeignatwork.backend.entity.User.Rolee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Rolee, Long> {

    Rolee findByName(String name);
}
