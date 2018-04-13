package org.txstate.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.txstate.edu.model.AccountSummary;
import org.txstate.edu.model.Accounts;
import org.txstate.edu.model.UsersProfile;
import org.txstate.edu.repository.AccountRepository;
import org.txstate.edu.repository.UserProfileRepository;

import java.util.List;

/**
 * Created by jyoti on 4/12/18.
 */
@Service
public class AccountService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    public AccountSummary getAccountSummary(String username) {
        UsersProfile userProfile = new UsersProfile();
        userProfile.setUsername(username);
        Example<UsersProfile> example = Example.of(userProfile);
        UsersProfile dbUsersProfile = userProfileRepository.findOne(example);

        Accounts accounts = new Accounts();
        accounts.setUsername(username);
        Example<Accounts> exampleAccounts = Example.of(accounts);
        List<Accounts> dbAccounts = accountRepository.findAll(exampleAccounts);

        AccountSummary accountSummary = new AccountSummary();
        accountSummary.setAccountName(dbUsersProfile.getFirstName() + " " + dbUsersProfile.getLastName());
        accountSummary.setAccounts(dbAccounts);
        accountSummary.setCurrency("$");
        return accountSummary;
    }

}
