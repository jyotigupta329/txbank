package org.txstate.edu.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jyoti on 4/22/18.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class NotificationPolicy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean enable;
    private Double creditAmount;
    private Double debitAmount;
    private Boolean profileUpdate;
    private Boolean passwordUpdate;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Boolean getProfileUpdate() {
        return profileUpdate;
    }

    public void setProfileUpdate(Boolean profileUpdate) {
        this.profileUpdate = profileUpdate;
    }

    public Boolean getPasswordUpdate() {
        return passwordUpdate;
    }

    public void setPasswordUpdate(Boolean passwordUpdate) {
        this.passwordUpdate = passwordUpdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
