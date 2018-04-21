package org.txstate.edu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "role"))
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
