package com.wellbeignatwork.backend.dto;

import com.wellbeignatwork.backend.entity.User.Userr;
import com.wellbeignatwork.backend.util.GeneralUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * @author Chinna
 */
public class LocalUser extends org.springframework.security.core.userdetails.User implements OAuth2User, OidcUser {

    /**
     *
     */
    private static final long serialVersionUID = -2845160792248762779L;
    private final OidcIdToken idToken;
    private final OidcUserInfo userInfo;
    private Map<String, Object> attributes;
    private Userr user;

    public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final Userr user) {
        this(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, user, null, null);
    }

    public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final Userr user, OidcIdToken idToken,
                     OidcUserInfo userInfo) {
        super(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
        this.idToken = idToken;
        this.userInfo = userInfo;
    }

    public static LocalUser create(Userr user, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
        LocalUser localUser = new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()),
                user, idToken, userInfo);
        localUser.setAttributes(attributes);
        return localUser;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return this.user.getDisplayName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Map<String, Object> getClaims() {
        return this.attributes;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.idToken;
    }

    public Userr getUser() {
        return user;
    }
}
