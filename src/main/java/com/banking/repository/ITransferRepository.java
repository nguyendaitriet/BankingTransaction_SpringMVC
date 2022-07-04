package com.banking.repository;

import com.banking.dto.TransferInfoDTO;
import com.banking.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ITransferRepository extends JpaRepository<Transfer, Long> {

    @Transactional
    @Query(value = "SELECT * FROM vw_transfer_information", nativeQuery = true)
//    @Procedure("sp_get_transfer_info")
    List<TransferInfoDTO> getTransferInfo();

}
