package com.banking.service;


import com.banking.dto.CustomerDTO;
import com.banking.model.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {

    List<CustomerDTO> findAllDTO();

    CustomerDTO findCustomerDTOById(long id);

    boolean addNewCustomer(String name, String email, String phone, String address);

    boolean updateCustomer(long id, String name, String email, String phone, String address);

    boolean existsByIdAndDeletedFalse(long id);

    void suspendCustomer(long id);

}
