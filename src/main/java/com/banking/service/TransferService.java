package com.banking.service;

import com.banking.dto.TransferInfoDTO;
import com.banking.model.Transfer;
import com.banking.repository.ITransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransferService implements ITransferService{

    @Autowired
    ITransferRepository transferRepository;

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return transferRepository.findById(id);
    }

    @Override
    public Transfer getById(Long id) {
        return transferRepository.getById(id);
    }

    @Override
    public void save(Transfer transfer) {
        transferRepository.save(transfer);
    }

    @Override
    public void remove(Long id) {
        transferRepository.deleteById(id);
    }

    @Override
    public List<TransferInfoDTO> getTransferInfo() {
        return transferRepository.getTransferInfo();
    }
}
