package com.banking.service;

import com.banking.dto.CustomerDTO;
import com.banking.model.Customer;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {

    List<CustomerDTO> findAllDTO();

    CustomerDTO findCustomerDTOById(long id);

    boolean addNewCustomer(String name, String email, String phone, String address);

    boolean updateCustomer(long id, String name, String email, String phone, String address);

    boolean existsByIdAndDeletedFalse(long id);

    void suspendCustomer(long id);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByPhoneAndIdIsNot(String phone, long id);

    boolean existsByEmailAndIdIsNot(String email, long id);

    List<Customer> findAllByIdIsNotAndDeletedFalse(long id);

}
