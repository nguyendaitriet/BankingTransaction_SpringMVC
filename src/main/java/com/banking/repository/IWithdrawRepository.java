package com.banking.repository;

import com.banking.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public interface IWithdrawRepository extends JpaRepository<Withdraw, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Customer c " +
            "SET c.balance = c.balance - :amount " +
            "WHERE c.id = :id")
    void withdraw(@Param("id") long id, @Param("amount") BigDecimal amount);
}
