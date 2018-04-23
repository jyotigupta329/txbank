package org.txstate.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.txstate.edu.model.AccountSummary;
import org.txstate.edu.model.Accounts;
import org.txstate.edu.model.BeneficiaryAccount;
import org.txstate.edu.model.UsersProfile;
import org.txstate.edu.repository.AccountRepository;
import org.txstate.edu.repository.BeneficiaryRepository;
import org.txstate.edu.repository.UserProfileRepository;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by jyoti on 4/12/18.
 */
@Service
public class AccountService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    /**
     * Provided the account summary of a user in the system.
     *
     * @param username
     * @return AccountSummary
     */
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

    public void createAccount(String username) {
        boolean createCheckingAccount = false;
        boolean createSavingAccount = false;

        Accounts accounts = new Accounts();
        accounts.setUsername(username);
        Example<Accounts> exampleAccounts = Example.of(accounts);
        List<Accounts> dbAccounts = accountRepository.findAll(exampleAccounts);

        for (Accounts acc : dbAccounts) {
            if (acc.getType().equals("CHECKING")) {
                createCheckingAccount = true;
            }
            if (acc.getType().equals("SAVING")) {
                createSavingAccount = true;
            }
        }

        Random rand = new Random();
        if (!createCheckingAccount) {
            Accounts checking = new Accounts();
            checking.setUsername(username);
            long randNumberChecking = rand.nextInt(999999999) + 100000000;
            checking.setAccountNo(Long.valueOf(randNumberChecking));
            checking.setType("CHECKING");
            checking.setBalance(5000.00);
            checking.setCreatedAt(new Date());
            checking.setUpdatedAt(new Date());
            accountRepository.save(checking);
        }

        if (!createSavingAccount) {
            Accounts saving = new Accounts();
            saving.setUsername(username);
            long randNumberSaving = rand.nextInt(555555555) + 100000000;
            saving.setAccountNo(Long.valueOf(randNumberSaving));
            saving.setType("SAVING");
            saving.setBalance(5000.00);
            saving.setCreatedAt(new Date());
            saving.setUpdatedAt(new Date());
            accountRepository.save(saving);
        }
    }

    public List<Accounts> getUserAccounts(String username) {
        return accountRepository.findByUsername(username);
    }

    public List<BeneficiaryAccount> getBeneficiaryAccounts(String username) {
        return beneficiaryRepository.getByUsername(username);
    }

    /**
     *
     * @param beneficiaryAccount
     * @param username
     */
    public void addBeneficiaryAccounts(BeneficiaryAccount beneficiaryAccount, String username) {
        Accounts accounts = accountRepository.findOne(beneficiaryAccount.getBeneficiaryAccountNo());
        if (accounts == null) {
            throw new RuntimeException("Invalid account no");
        }

        List<BeneficiaryAccount> beneficiaryAccounts = beneficiaryRepository.getByUsername(username);
        for (BeneficiaryAccount account : beneficiaryAccounts) {
            if (account.getBeneficiaryAccountNo().equals(beneficiaryAccount.getBeneficiaryAccountNo())) {
                throw new RuntimeException("Beneficiary already exists");
            }
        }
        beneficiaryAccount.setUsername(username);
        beneficiaryRepository.save(beneficiaryAccount);
    }
}
