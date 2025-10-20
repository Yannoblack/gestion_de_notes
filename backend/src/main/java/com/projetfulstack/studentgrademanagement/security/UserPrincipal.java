package com.projetfulstack.studentgrademanagement.security;

import com.projetfulstack.studentgrademanagement.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * User Principal
 * Custom UserDetails implementation for Spring Security
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
public class UserPrincipal implements UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String firstName, String lastName, String username, 
                        String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Create UserPrincipal from User entity
     */
    public static UserPrincipal create(User user) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        return new UserPrincipal(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
