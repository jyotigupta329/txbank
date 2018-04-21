package org.txstate.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class UsersIdentity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String idtype1;
    private String idno1;
    private String idtype2;
    private String idno2;
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdtype1() {
        return idtype1;
    }

    public void setIdtype1(String idtype1) {
        this.idtype1 = idtype1;
    }

    public String getIdno1() {
        return idno1;
    }

    public void setIdno1(String idno1) {
        this.idno1 = idno1;
    }

    public String getIdtype2() {
        return idtype2;
    }

    public void setIdtype2(String idtype2) {
        this.idtype2 = idtype2;
    }

    public String getIdno2() {
        return idno2;
    }

    public void setIdno2(String idno2) {
        this.idno2 = idno2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
