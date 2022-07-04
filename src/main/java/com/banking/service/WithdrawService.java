package com.banking.service;

import com.banking.model.Withdraw;
import com.banking.repository.IWithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawService implements IWithdrawService{

    @Autowired
    IWithdrawRepository withdrawRepository;

    @Override
    public List<Withdraw> findAll() {
        return withdrawRepository.findAll();
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return withdrawRepository.findById(id);
    }

    @Override
    public Withdraw getById(Long id) {
        return withdrawRepository.getById(id);
    }

    @Override
    public void save(Withdraw withdraw) {
        withdrawRepository.save(withdraw);
    }

    @Override
    public void remove(Long id) {
        withdrawRepository.deleteById(id);
    }

    @Override
    public void withdraw(long id, BigDecimal amount) {
        withdrawRepository.withdraw(id, amount);
    }
}
