package org.txstate.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.txstate.edu.model.Accounts;
import org.txstate.edu.model.Transaction;
import org.txstate.edu.model.Users;
import org.txstate.edu.repository.AccountRepository;
import org.txstate.edu.repository.TransactionRepository;
import org.txstate.edu.repository.UserRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by jyoti on 4/21/18.
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    /**
     *
     * @param transaction
     */
    public void doTransaction(Transaction transaction) {
        Accounts accountFrom = accountRepository.findOne(transaction.getFromAccount());

        Accounts accountTo = accountRepository.findOne(transaction.getToAccount());

        Users users = userRepository.findByUsername(transaction.getCreatedBy());

        if (accountFrom == null || accountTo == null) {
            throw new RuntimeException("Invalid Account Number");
        }
        if (accountFrom.getAccountNo().equals(accountTo.getAccountNo())) {
            throw new RuntimeException("Cannot transfer to same account");
        }
        if (accountFrom.getBalance() < transaction.getAmount()) {
            throw new RuntimeException("Insufficient Balance");
        }
        if (transaction.getAmount() <= 0) {
            throw new RuntimeException("Invalid Amount");
        }
        if (users == null) {
            throw new RuntimeException("Unauthorized Access");
        }

        accountFrom.setBalance(accountFrom.getBalance() - transaction.getAmount());
        accountFrom.setUpdatedAt(new Date());
        accountRepository.save(accountFrom);

        accountTo.setBalance(accountTo.getBalance() + transaction.getAmount());
        accountTo.setUpdatedAt(new Date());
        accountRepository.save(accountTo);

        transaction.setCreatedDate(new Date());
        transaction.setTransactionType("DEBIT");
        transactionRepository.save(transaction);
        Transaction toTransaction = new Transaction();

        toTransaction.setFromAccount(transaction.getToAccount());
        toTransaction.setAmount(transaction.getAmount());
        toTransaction.setCreatedBy(transaction.getCreatedBy());
        toTransaction.setToAccount(transaction.getFromAccount());
        toTransaction.setTransactionType("CREDIT");
        toTransaction.setCreatedDate(new Date());
        toTransaction.setMessage("Money deposited.");
        transactionRepository.save(toTransaction);

        notificationService.notifyOnTransaction(transaction, accountFrom, accountTo);
    }

    /**
     *
     * @param username
     * @return
     */
    public List<Transaction> getTransactionByUsername(String username) {
        return transactionRepository.getByCreatedBy(username);
    }

    /**
     *
     * @param accountNo
     * @return
     */
    public List<Transaction> getTransactionByAccount(Long accountNo) {
        return transactionRepository.getByFromAccount(accountNo);
    }

}
