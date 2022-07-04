package com.banking.service;

import com.banking.model.Deposit;
import com.banking.repository.IDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class DepositService implements IDepositService{
    @Autowired
    IDepositRepository depositRepository;

    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return depositRepository.findById(id);
    }

    @Override
    public Deposit getById(Long id) {
        return depositRepository.getById(id);
    }

    @Override
    public void save(Deposit deposit) {
        depositRepository.save(deposit);
    }

    @Override
    public void remove(Long id) {
        depositRepository.deleteById(id);
    }


    @Override
    public void deposit(long id, BigDecimal amount) {
        depositRepository.deposit(id, amount);
    }
}
