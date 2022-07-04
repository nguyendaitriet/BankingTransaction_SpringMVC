package com.banking.service;

import com.banking.dto.TransferInfoDTO;
import com.banking.model.Transfer;

import java.util.List;

public interface ITransferService extends IGeneralService<Transfer>{
    List<TransferInfoDTO> getTransferInfo ();

}
