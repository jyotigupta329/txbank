package org.txstate.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.txstate.edu.model.Roles;
import org.txstate.edu.model.Users;
import org.txstate.edu.model.UsersProfile;
import org.txstate.edu.repository.UserProfileRepository;
import org.txstate.edu.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jyoti on 2/8/18.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Users getByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(Users user) {
        userRepository.save(user);
    }

    public void addUserProfile(UsersProfile usersProfile) {
        userProfileRepository.save(usersProfile);
    }

    public void updateUser(Users user, String username) {
        Users userobj = userRepository.findByUsername(username);
        userobj.setPassword(user.getPassword());
        userRepository.save(user);
    }


    public void deleteUser(String username) {
        userRepository.delete(username);
    }

    /**
     * Spring Security related methods
     * Below methods are meant to be used by spring internally
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.isEnable(), user.isAccountNonExpired(), true, user.isAccountNonLocked(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Roles> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

}
