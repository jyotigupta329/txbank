package org.txstate.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.txstate.edu.model.AccountSummary;
import org.txstate.edu.service.AccountService;

/**
 * Created by jyoti on 4/12/18.
 */

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/accountSummary/{username}")
    public AccountSummary getAccountSummary(@PathVariable String username) {
        return accountService.getAccountSummary(username);
    }
}
