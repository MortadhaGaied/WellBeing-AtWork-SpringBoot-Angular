package com.wellbeignatwork.backend.service.UserService;


import com.wellbeignatwork.backend.entity.User.PasswordResetToken;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.repository.User.PasswordTokenRepository;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }


    public void changeUserPassword(User user, String password) {
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    public void forgetPassword(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        String passwordRestUrl = "http://localhost:8080/api/auth/reset-password?token=" + token;
        mailService.sendMail(userEmail, "Reset your password", passwordRestUrl, false);
    }

}
