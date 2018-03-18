package org.txstate.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.txstate.edu.model.Users;
import org.txstate.edu.service.UserService;

import java.util.List;

/**
 * Created by jyoti on 2/8/18.
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping("/users/{username}")
    public Users getByUserName(@PathVariable String username) {
        return userService.getByUserName(username);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void addUser(@RequestBody Users user) {
        userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{username}")
    public void updateUser(@RequestBody Users user, @PathVariable String username) {
        userService.updateUser(user, username);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

}