package com.banking.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "withdraws")
public class Withdraw implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12)
    @NotNull(message = "Transaction amount must NOT be empty.")
    @Digits(integer = 12, fraction = 0,
            message = "Maximum digit of transaction amount is 12.")
    @Min(value = 100,message = "Transaction amount must NOT be LESS than 100.")
    private BigDecimal transactionAmount;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    private Long createdBy;

    @UpdateTimestamp
    private Date updatedAt;

    private Long updatedBy;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public Withdraw() {
        this.customer = new Customer((long) 0);
    }

    public Withdraw(BigDecimal transactionAmount, Date createdAt, long createdBy, Date updatedAt, long updatedBy, boolean deleted) {
        this.transactionAmount = transactionAmount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Withdraw.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Withdraw withdraw = (Withdraw) target;
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        BigDecimal balance = withdraw.getCustomer().getBalance();

        if (withdraw.getId() == null) {
            errors.rejectValue("transactionAmount", "transactionAmount.nullAmount");
        }

        if (transactionAmount.compareTo(new BigDecimal(100)) < 0) {
            errors.rejectValue("transactionAmount","transactionAmount.minimumTransactionAmount");
        }

        if (transactionAmount.compareTo(balance) > 0) {
            errors.rejectValue("transactionAmount", "transactionAmount.maximumTransactionAmount");
        }

        if (transactionAmount.precision() > 12 || transactionAmount.scale() > 0) {
            errors.rejectValue("transactionAmount", "transactionAmount.validFormat");
        }
    }
}
