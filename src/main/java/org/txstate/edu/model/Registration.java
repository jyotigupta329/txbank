package org.txstate.edu.model;

import java.io.Serializable;

/**
 * Created by jyoti on 4/3/18.
 */
public class Registration extends UsersProfile implements Serializable {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
