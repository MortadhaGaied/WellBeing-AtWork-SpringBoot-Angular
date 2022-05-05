package com.wellbeignatwork.backend.util;


import com.wellbeignatwork.backend.dto.LocalUser;
import com.wellbeignatwork.backend.dto.SocialProvider;
import com.wellbeignatwork.backend.dto.UserInfo;
import com.wellbeignatwork.backend.entity.User.Rolee;
import com.wellbeignatwork.backend.entity.User.Userr;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Chinna
 */
public class GeneralUtils {

    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Rolee> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Rolee role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public static SocialProvider toSocialProvider(String providerId) {
        for (SocialProvider socialProvider : SocialProvider.values()) {
            if (socialProvider.getProviderType().equals(providerId)) {
                return socialProvider;
            }
        }
        return SocialProvider.LOCAL;
    }

    public static UserInfo buildUserInfo(LocalUser localUser) {
        List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        Userr user = localUser.getUser();
        return new UserInfo(user.getId().toString(), user.getDisplayName(), user.getEmail(), roles);
    }
}
