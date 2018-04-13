package org.txstate.edu.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jyoti on 4/12/18.
 */
public class AccountSummary implements Serializable {

    private String accountName;
    private String currency;
    private List<Accounts> accounts;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Accounts> accounts) {
        this.accounts = accounts;
    }
}
