package com.banking.repository;


import com.banking.dto.CustomerDTO;
import com.banking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT NEW com.banking.dto.CustomerDTO (" +
                "c.id, " +
                "c.fullName, " +
                "c.email, " +
                "c.phone, " +
                "c.address, " +
                "c.balance, " +
                "c.deleted) " +
            "FROM Customer c " +
            "WHERE c.deleted = false")
    List<CustomerDTO> findAllDTO();

    @Query("SELECT NEW com.banking.dto.CustomerDTO (" +
            "c.id, " +
            "c.fullName, " +
            "c.email, " +
            "c.phone, " +
            "c.address, " +
            "c.balance, " +
            "c.deleted) " +
            "FROM Customer c " +
            "WHERE c.id = :id")
    CustomerDTO findCustomerDTOById(@Param("id") long id);

    @Procedure("sp_add_new_customer")
    boolean addNewCustomer(String name, String email, String phone, String address);

}
