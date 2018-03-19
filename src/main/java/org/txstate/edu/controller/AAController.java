package org.txstate.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.txstate.edu.model.LoginModel;
import org.txstate.edu.service.AAService;

/**
 * Created by jyoti on 2/24/18.
 * Authentication and Authorization controller
 */

@RestController
public class AAController {

    @Autowired
    private AAService aaService;

}
