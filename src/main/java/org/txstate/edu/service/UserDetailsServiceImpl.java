package org.txstate.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.txstate.edu.model.*;
import org.txstate.edu.repository.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jyoti on 2/8/18.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UsersIdentityRepository usersIdentityRepository;

    @Autowired
    private NotificationPolicyRepository notificationPolicyRepository;

    public Users getByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserForm> getAllUsers() {
        List<UserForm> userForms = new ArrayList<>();
        List<Users> users = userRepository.findAll();
        for (Users user : users) {
            UserForm userForm = new UserForm();
            userForm.setUsername(user.getUsername());
            userForm.setEnable(user.isEnable());
            userForm.setAccountNonExpired(user.isAccountNonExpired());
            userForm.setAccountNonLocked(user.isAccountNonLocked());

            UsersProfile query_usersProfile = new UsersProfile();
            query_usersProfile.setUsername(user.getUsername());
            Example<UsersProfile> example = Example.of(query_usersProfile);
            UsersProfile usersProfile = userProfileRepository.findOne(example);

            if (usersProfile != null) {
                userForm.setFirstName(usersProfile.getFirstName());
                userForm.setLastName(usersProfile.getLastName());
                userForm.setUsername(usersProfile.getUsername());
                userForm.setAddress1(usersProfile.getAddress1());
                userForm.setAddress2(usersProfile.getAddress2());
                userForm.setGender(usersProfile.getGender());
                userForm.setCity(usersProfile.getCity());
                userForm.setState(usersProfile.getState());
                userForm.setZip(usersProfile.getZip());
                userForm.setCountry(usersProfile.getCountry());
                userForm.setNationality(usersProfile.getNationality());
                userForm.setEmail(usersProfile.getEmail());
                userForm.setPhone(usersProfile.getPhone());
                userForm.setDob(usersProfile.getDob());
            }

            UsersIdentity query_usersIdentity = new UsersIdentity();
            query_usersIdentity.setUsername(user.getUsername());
            Example<UsersIdentity> exampleIdentity = Example.of(query_usersIdentity);
            UsersIdentity usersIdentity = usersIdentityRepository.findOne(exampleIdentity);

            if (usersIdentity != null) {
                userForm.setIdno1(usersIdentity.getIdno1());
                userForm.setIdno2(usersIdentity.getIdno2());
                userForm.setIdtype1(usersIdentity.getIdtype1());
                userForm.setIdtype2(usersIdentity.getIdtype2());
            }
            userForms.add(userForm);
        }

        return userForms;
    }

    public void addUser(Users user) {
        Roles userRole = rolesRepository.findByRole("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public void addUserProfile(UsersProfile usersProfile) {
        userProfileRepository.save(usersProfile);
    }

    public void addUsersIdentity(UsersIdentity usersIdentity) {
        usersIdentityRepository.save(usersIdentity);
    }

    public void updateUser(Users user, String username) {
        Users dbUser = userRepository.findByUsername(username);
        dbUser.setPassword(user.getPassword());
        userRepository.save(dbUser);
    }


    public void deleteUser(String username) {
        Users dbUser = userRepository.findByUsername(username);
        userRepository.delete(dbUser);
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

    public void updateUserProfile(UsersProfile usersProfile, String username) {

        UsersProfile query_usersProfile = new UsersProfile();
        query_usersProfile.setUsername(username);
        Example<UsersProfile> example = Example.of(query_usersProfile);
        UsersProfile dbUsersProfile = userProfileRepository.findOne(example);

        dbUsersProfile.setPhone(usersProfile.getPhone());
        dbUsersProfile.setEmail(usersProfile.getEmail());
        dbUsersProfile.setZip(usersProfile.getZip());
        dbUsersProfile.setCity(usersProfile.getCity());
        dbUsersProfile.setState(usersProfile.getState());
        dbUsersProfile.setAddress1(usersProfile.getAddress1());
        dbUsersProfile.setAddress2(usersProfile.getAddress2());
        dbUsersProfile.setNationality(usersProfile.getNationality());
        userProfileRepository.save(dbUsersProfile);
    }

    public UsersProfile getProfileByUserName(String username) {

        UsersProfile query_usersProfile = new UsersProfile();
        query_usersProfile.setUsername(username);
        Example<UsersProfile> example = Example.of(query_usersProfile);
        return userProfileRepository.findOne(example);


    }

    public void updateUserPassword(Users users, String username) {

        Users query_users = new Users();
        query_users.setUsername(username);
        query_users.setEnable(true);
        query_users.setAccountNonExpired(true);
        query_users.setAccountNonLocked(true);
        Example<Users> example = Example.of(query_users);
        Users dbUsers = userRepository.findOne(example);

        dbUsers.setPassword(users.getPassword());
        userRepository.save(dbUsers);

    }


    public void activateAccount(String username) {
        Users user = userRepository.findByUsername(username);
        user.setEnable(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        userRepository.save(user);


        accountService.createAccount(username);
        NotificationPolicy notificationPolicy = notificationPolicyRepository.findByUsername(username);
        if (notificationPolicy == null) {
            notificationPolicy = new NotificationPolicy();
            notificationPolicy.setCreditAmount(50D);
            notificationPolicy.setDebitAmount(50D);
            notificationPolicy.setEnable(true);
            notificationPolicy.setPasswordUpdate(true);
            notificationPolicy.setProfileUpdate(true);
            notificationPolicy.setUsername(username);
            notificationPolicyRepository.save(notificationPolicy);
        }
    }

    public void suspendAccount(String username) {
        Users user = userRepository.findByUsername(username);
        user.setEnable(false);
        user.setAccountNonLocked(false);
        user.setAccountNonExpired(false);
        userRepository.save(user);

        accountService.createAccount(username);
    }

}
