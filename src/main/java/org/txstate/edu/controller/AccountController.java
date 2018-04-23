package org.txstate.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.txstate.edu.model.*;
import org.txstate.edu.service.AccountService;
import org.txstate.edu.service.NotificationService;
import org.txstate.edu.service.TransactionService;

import java.util.List;

/**
 * Created by jyoti on 4/12/18.
 */

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TransactionService transactionService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/accountSummary/{username}")
    public AccountSummary getAccountSummary(@PathVariable String username) {
        return accountService.getAccountSummary(username);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/accounts")
    public List<Accounts> getAccounts() {
        return accountService.getUserAccounts(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/accounts/beneficiary")
    public List<BeneficiaryAccount> getAccountsBeneficiary() {
        return accountService.getBeneficiaryAccounts(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.POST, value = "/accounts/beneficiary")
    public void addAccountsBeneficiary(@RequestBody BeneficiaryAccount beneficiaryAccount) {
        accountService.addBeneficiaryAccounts(beneficiaryAccount, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.POST, value = "/transaction")
    public void doTransaction(@RequestBody Transaction transaction) {
        transactionService.doTransaction(transaction);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/transaction")
    public List<Transaction> getTransaction() {
        return transactionService.getTransactionByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/transaction/{accountNo}")
    public List<Transaction> getTransactionByAccountNo(@PathVariable Long accountNo) {
        return transactionService.getTransactionByAccount(accountNo);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/notification")
    public NotificationPolicy getNotificationPolicy() {
       return notificationService.getNotificationPolicy(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.PUT, value = "/notification")
    public void updateNotificationPolicy(@RequestBody NotificationPolicy notificationPolicy) {
        notificationService.updateNotificationPolicy(notificationPolicy);
    }

}
