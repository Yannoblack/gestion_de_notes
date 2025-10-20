package com.projetfulstack.studentgrademanagement.security;

import com.projetfulstack.studentgrademanagement.entity.User;
import com.projetfulstack.studentgrademanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom User Details Service
 * Loads user details for Spring Security authentication
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByEmail(usernameOrEmail)
                .orElseThrow(() -> 
                    new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );

        return UserPrincipal.create(user);
    }

    /**
     * Load user details by user ID
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}
