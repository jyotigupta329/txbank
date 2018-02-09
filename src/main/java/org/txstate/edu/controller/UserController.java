package org.txstate.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}