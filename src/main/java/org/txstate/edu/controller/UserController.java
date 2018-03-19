package org.txstate.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.txstate.edu.model.Users;
import org.txstate.edu.service.UserDetailsServiceImpl;

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

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDetailsServiceImpl.addUser(user);
    }

    @RequestMapping("/users")
    public List<Users> getAllUsers() {
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

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{username}")
    public void deleteUser(@PathVariable String username) {
        userDetailsServiceImpl.deleteUser(username);
    }

}