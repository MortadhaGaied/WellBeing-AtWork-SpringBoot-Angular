package com.wellbeignatwork.backend.repository.User;

import com.wellbeignatwork.backend.entity.User.PasswordResetToken;
import com.wellbeignatwork.backend.entity.User.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(Userr user);


}
