package com.banking.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12)
    @NotNull(message = "Transaction amount must NOT be empty.")
    @Digits(integer = 12, fraction = 0,
            message = "Maximum digit of transaction amount is 12.")
    @Min(value = 100,message = "Transaction amount must NOT be LESS than 100.")
    private BigDecimal transferAmount;

    @Column(nullable = false, precision = 12)
    @NotNull(message = "Total amount must NOT be empty.")
    @Digits(integer = 12, fraction = 0,
            message = "Maximum digit of Total amount is 12.")
    private BigDecimal transactionAmount;

    private int fees;

    @Column(precision = 12)
    private BigDecimal feesAmount;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    private long createdBy;

    @UpdateTimestamp
    private Date updatedAt;

    private long updatedBy;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private  Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private  Customer recipient;

    public Transfer() {
        this.sender = new Customer((long) 0);
        this.recipient = new Customer((long) 0);
    }

    public Transfer(Long id, BigDecimal transactionAmount, BigDecimal transferAmount, int fees, BigDecimal feesAmount, Date createdAt, long createdBy, Date updatedAt, long updatedBy, boolean deleted, Customer sender, Customer recipient) {
        this.id = id;
        this.transactionAmount = transactionAmount;
        this.transferAmount = transferAmount;
        this.fees = fees;
        this.feesAmount = feesAmount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
        this.sender = sender;
        this.recipient = recipient;
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

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public BigDecimal getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(BigDecimal feesAmount) {
        this.feesAmount = feesAmount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }
}
