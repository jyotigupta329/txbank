package org.txstate.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.txstate.edu.model.UserForm;
import org.txstate.edu.model.Users;
import org.txstate.edu.model.UsersIdentity;
import org.txstate.edu.model.UsersProfile;
import org.txstate.edu.service.UserDetailsServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * Created by jyoti on 2/8/18.
 */

@RestController
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/users/sign-up")
    public void signUp(@RequestBody UserForm registerForm) {
        //Cresting user object
        try {
            Users user = new Users();
            user.setPassword(bCryptPasswordEncoder.encode(registerForm.getPassword()));
            user.setUsername(registerForm.getUsername());
            userDetailsServiceImpl.addUser(user);

            UsersProfile usersProfile = new UsersProfile();
            usersProfile.setFirstName(registerForm.getFirstName());
            usersProfile.setLastName(registerForm.getLastName());
            usersProfile.setUsername(registerForm.getUsername());
            usersProfile.setAddress1(registerForm.getAddress1());
            usersProfile.setAddress2(registerForm.getAddress2());
            usersProfile.setGender(registerForm.getGender());
            usersProfile.setCity(registerForm.getCity());
            usersProfile.setState(registerForm.getState());
            usersProfile.setZip(registerForm.getZip());
            usersProfile.setCountry(registerForm.getCountry());
            usersProfile.setNationality(registerForm.getNationality());
            usersProfile.setEmail(registerForm.getEmail());
            usersProfile.setPhone(registerForm.getPhone());
            usersProfile.setDob(registerForm.getDob());
            usersProfile.setCreatedAt(new Date());
            usersProfile.setCreatedBy(registerForm.getUsername());
            usersProfile.setUpdatedAt(new Date());
            usersProfile.setUpdateBy(registerForm.getUsername());
            userDetailsServiceImpl.addUserProfile(usersProfile);

            UsersIdentity usersIdentity = new UsersIdentity();
            usersIdentity.setIdno1(registerForm.getIdno1());
            usersIdentity.setIdno2(registerForm.getIdno2());
            usersIdentity.setIdtype1(registerForm.getIdtype1());
            usersIdentity.setIdtype2(registerForm.getIdtype2());
            usersIdentity.setUsername(registerForm.getUsername());
            userDetailsServiceImpl.addUsersIdentity(usersIdentity);

        } catch (Exception e) {
            // e.printStackTrace();
            throw new RuntimeException("Duplicate entry for username " + registerForm.getUsername());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/users")
    public List<UserForm> getAllUsers() {
        return userDetailsServiceImpl.getAllUsers();
    }

    @RequestMapping("/users/{username}")
    public Users getByUserName(@PathVariable String username) {
        return userDetailsServiceImpl.getByUserName(username);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void addUser(@RequestBody Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDetailsServiceImpl.addUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{username}")
    public void updateUser(@RequestBody Users user, @PathVariable String username) {
        userDetailsServiceImpl.updateUser(user, username);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{username}")
    public void deleteUser(@PathVariable String username) {
        userDetailsServiceImpl.deleteUser(username);
    }

//    Update Profile

    @RequestMapping(method = RequestMethod.PUT, value = "/users/profile/{username}")
    public void updateUserProfile(@RequestBody UsersProfile usersProfile, @PathVariable String username) {
        userDetailsServiceImpl.updateUserProfile(usersProfile, username);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/profile/{username}")
    public UsersProfile getUsersProfile(@PathVariable String username) {
        return userDetailsServiceImpl.getProfileByUserName(username);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/changePassword/{username}")
    public void updateUserPassword(@RequestBody Users users, @PathVariable String username) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        userDetailsServiceImpl.updateUserPassword(users, username);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/users/activateAccount/{username}")
    public void activateAccount(@PathVariable String username) {
        userDetailsServiceImpl.activateAccount(username);
    }

}