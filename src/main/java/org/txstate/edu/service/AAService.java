package org.txstate.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.txstate.edu.config.SecurityConfig;
import org.txstate.edu.model.Users;
import org.txstate.edu.repository.UserRepository;

/**
 * Created by jyoti on 2/24/18.
 *
 * Authentication and authorization service
 */

@Service
public class AAService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;

    public boolean authenticate(String username, String password) {
        Users users = userRepository.findByUsername(username);
        if(users == null) {
            return false;
        }
        boolean isPasswordMatch = securityConfig.passwordEncoder().matches(password, users.getPassword());
        return isPasswordMatch;
    }


}
