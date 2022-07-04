package com.banking.service;

import com.banking.model.Withdraw;

import java.math.BigDecimal;

public interface IWithdrawService extends IGeneralService<Withdraw>{

    void withdraw(long id, BigDecimal amount);

}
