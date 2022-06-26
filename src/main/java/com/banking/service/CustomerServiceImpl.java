package com.banking.service;

import com.banking.dto.CustomerDTO;
import com.banking.model.Customer;
import com.banking.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerDTO> findAllDTO() {
        return customerRepository.findAllDTO();
    }

    @Override
    public CustomerDTO findCustomerDTOById(long id) {
        return customerRepository.findCustomerDTOById(id);
    }

    @Override
    public boolean addNewCustomer(String name, String email, String phone, String address) {
        return customerRepository.addNewCustomer(name, email, phone, address);
    }

    @Override
    public boolean updateCustomer(long id, String name, String email, String phone, String address) {
        return customerRepository.updateCustomer(id, name, email,phone,address);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void remove(Long id) {
        customerRepository.deleteById(id);
    }
    @Override
    public boolean existsByIdAndDeletedFalse(long id) {
        return customerRepository.existsByIdAndDeletedFalse(id);
    }

    @Override
    public void suspendCustomer(long id) {
        customerRepository.suspendCustomer(id);
    }
}