package com.banking.service;

import com.banking.model.Deposit;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface IDepositService extends IGeneralService<Deposit> {

    void deposit(long id, BigDecimal amount);

}
