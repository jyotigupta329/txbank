package org.txstate.edu.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jyoti on 4/3/18.
 */
public class UserForm implements Serializable {

    private String username;
    private String password;
    private boolean enable;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private Long zip;
    private String country;
    private String nationality;
    private Date dob;
    private String gender;
    private String email;
    private String phone;
    private String idtype1;
    private String idno1;
    private String idtype2;
    private String idno2;

    private String accountStatus;

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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        if (enable) {
            this.accountStatus = "Active";
        } else {
            this.accountStatus = "Pending";
        }
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getZip() {
        return zip;
    }

    public void setZip(Long zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}
