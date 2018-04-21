package org.txstate.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.txstate.edu.model.AccountSummary;
import org.txstate.edu.model.Transaction;
import org.txstate.edu.service.AccountService;
import org.txstate.edu.service.TransactionService;

/**
 * Created by jyoti on 4/12/18.
 */

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/accountSummary/{username}")
    public AccountSummary getAccountSummary(@PathVariable String username) {
        return accountService.getAccountSummary(username);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/transaction")
    public void doTransaction(@RequestBody Transaction transaction) {
        transactionService.doTransaction(transaction);
    }
}
