package com.wellbeignatwork.backend.config.UserConfig;

import com.wellbeignatwork.backend.entity.User.Rolee;
import com.wellbeignatwork.backend.repository.User.RoleRepository;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        // Create initial roles
        Rolee userRole = createRoleIfNotFound(Rolee.ROLE_USER);
        Rolee adminRole = createRoleIfNotFound(Rolee.ROLE_ADMIN);
        Rolee modRole = createRoleIfNotFound(Rolee.ROLE_MODERATOR);
        alreadySetup = true;
    }

    @Transactional
    public Rolee createRoleIfNotFound(final String name) {
        Rolee role = roleRepository.findByName(name);
        if (role == null) {
            role = roleRepository.save(new Rolee(name));
        }
        return role;
    }
}