package com.banking.dto;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class CustomerDTO {

    private Long id;

    @NotEmpty
    @Length(min =1, max = 200,
            message = "Max characters of name: 200.")
    @Pattern(regexp = "^[A-Za-z]*",
            message = "Name contains only letter and whitespace.")
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private BigDecimal balance;
    private boolean deleted;

    public CustomerDTO() {
    }

    public CustomerDTO(long id, String fullName, String email, String phone, String address, BigDecimal balance, boolean deleted) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
