package com.wellbeignatwork.backend.service;


import com.wellbeignatwork.backend.dto.LocalUser;
import com.wellbeignatwork.backend.dto.SignUpRequest;
import com.wellbeignatwork.backend.exception.UserAlreadyExistAuthenticationException;
import com.wellbeignatwork.backend.model.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;

/**
 * @author Chinna
 * @since 26/3/18
 */
public interface UserService {

    public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    User findUserByEmail(String email);

    User findUserById(Long id);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

    public void deleteUser(Long id);

    public String confirmToken(String token);

}
