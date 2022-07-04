package com.banking.controller;

import com.banking.dto.CustomerDTO;
import com.banking.dto.TransferInfoDTO;
import com.banking.model.Transfer;
import com.banking.repository.IDepositRepository;
import com.banking.service.ICustomerService;
import com.banking.service.IDepositService;
import com.banking.service.ITransferService;
import com.banking.service.IWithdrawService;
import com.banking.util.Error;
import com.banking.util.ParsingValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class TransferController {
    private static final int fees = 10;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;

    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private ITransferService transferService;


    @GetMapping("/transfer/{id}")
    public ModelAndView showWithdrawForm(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("/transfer");

        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);

            if (customerService.existsByIdAndDeletedFalse(validId)) {
                Transfer newTransfer = new Transfer();
                setSenderForTransfer(newTransfer, validId);

                newTransfer.setFees(fees);

                modelAndView.addObject("recipientList", customerService.findAllByIdIsNotAndDeletedFalse(validId));

                modelAndView.addObject("newTransfer", newTransfer);
                return modelAndView;
            }

        }

        modelAndView.addObject("wrongId", "Sender ID doesn't exist!");
        return modelAndView;
    }

    @PostMapping("/transfer/{id}")
    public ModelAndView transfer(@PathVariable String id,
                                 @RequestParam String recipientId,
                                 @Validated @ModelAttribute("newTransfer") Transfer newTransfer,
                                 BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("/transfer");
        newTransfer.setFees(fees);

        try {
            if (ParsingValidationUtils.isLongParsable(id) && ParsingValidationUtils.isLongParsable(recipientId)) {
                long validSenderId = Long.parseLong(id);
                long validRecipientId = Long.parseLong(recipientId);
                setSenderForTransfer(newTransfer, validSenderId);
                addRecipientList(modelAndView,validSenderId);

                if (customerService.existsByIdAndDeletedFalse(validSenderId) && customerService.existsByIdAndDeletedFalse(validRecipientId)) {
                    if (validSenderId != validRecipientId) {

                        setRecipientForTransfer(newTransfer, validRecipientId);
                        BigDecimal transferAmount = newTransfer.getTransferAmount();
                        BigDecimal feeAmount = transferAmount.multiply(new BigDecimal(fees)).divide(new BigDecimal(100));
                        BigDecimal transactionAmount = transferAmount.add(feeAmount);

                        if (newTransfer.getSender().getBalance().compareTo(transactionAmount) < 0) {
                            bindingResult.addError(Error.NOT_ENOUGH_BALANCE);
                        }

                        if (!bindingResult.hasErrors()) {

                            withdrawService.withdraw(validSenderId, transactionAmount);
                            depositService.deposit(validRecipientId, transferAmount);

                            newTransfer.setFeesAmount(feeAmount);
                            transferService.save(newTransfer);

                            modelAndView.addObject("success", "Successful operation!");
                            setSenderForTransfer(newTransfer, validSenderId);
                            addRecipientList(modelAndView,validSenderId);
                            modelAndView.addObject("newTransfer", newTransfer);
                            return modelAndView;
                        }
                        setSenderForTransfer(newTransfer, validSenderId);
                        addRecipientList(modelAndView,validSenderId);

                    } else {
                        bindingResult.addError(Error.DUPLICATE_TRANSFER_CUSTOMER);
                    }

                    modelAndView.addObject("hasError", true);
                    return modelAndView;
                }
            }

            modelAndView.addObject("wrongId", "Customer ID doesn't exist!");
            modelAndView.addObject("newTransfer", new Transfer());
            return modelAndView;

        } catch (Exception ex) {
            modelAndView.addObject("failure", "Failed operation!");
            return modelAndView;
        }
    }

    @GetMapping("/transferInfo")
    public ModelAndView showTransferInfo() {
        ModelAndView modelAndView = new ModelAndView("/transferInfo");
        List<TransferInfoDTO> transferInfoDTO = transferService.getTransferInfo();
        modelAndView.addObject("transferInfoDTO", transferInfoDTO);
        return modelAndView;
    }

    private void setSenderForTransfer(Transfer transfer, long senderId) {
        transfer.setSender(customerService.findById(senderId).get());
    }

    private void setRecipientForTransfer(Transfer transfer, long recipientId) {
        transfer.setRecipient(customerService.findById(recipientId).get());
    }

    private void addRecipientList(ModelAndView modelAndView, long validSenderId) {
        modelAndView.addObject("recipientList", customerService.findAllByIdIsNotAndDeletedFalse(validSenderId));
    }
}
