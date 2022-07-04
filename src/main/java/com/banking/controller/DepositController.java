package com.banking.controller;

import com.banking.model.Deposit;
import com.banking.service.ICustomerService;
import com.banking.service.IDepositService;
import com.banking.util.Error;
import com.banking.util.ParsingValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
public class DepositController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;

    @GetMapping("/deposit/{id}")
    public ModelAndView showDepositForm(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("/deposit");

        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);

            if (customerService.existsByIdAndDeletedFalse(validId)) {
                Deposit newDeposit = new Deposit();
                setCustomerForDeposit(newDeposit, validId);
                modelAndView.addObject("newDeposit", newDeposit);
                return modelAndView;
            }
        }

        modelAndView.addObject("wrongId", "Customer ID doesn't exist!");
        return modelAndView;
    }

    @PostMapping("/deposit/{id}")
    public ModelAndView deposit(@PathVariable String id,
                                @Validated @ModelAttribute("newDeposit") Deposit newDeposit,
                                BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("/deposit");
        try {
            if (ParsingValidationUtils.isLongParsable(id)) {
                long validId = Long.parseLong(id);
                setCustomerForDeposit(newDeposit, validId);

                if (customerService.existsByIdAndDeletedFalse(validId)) {
                    BigDecimal amount = newDeposit.getTransactionAmount();

                    if (!bindingResult.hasErrors()) {

                        depositService.deposit(validId, amount);
                        depositService.save(newDeposit);

                        modelAndView.addObject("success", "Successful operation!");
                        setCustomerForDeposit(newDeposit, validId);
                        modelAndView.addObject("newDeposit", newDeposit);
                        return modelAndView;
                    }

                    modelAndView.addObject("newDeposit", newDeposit);
                    modelAndView.addObject("hasError", true);
                    return modelAndView;
                }
            }

            bindingResult.addError(Error.WRONG_ID);
            modelAndView.addObject("hasError", true);
            return modelAndView;

        } catch (Exception ex) {
            modelAndView.addObject("failure", "Failed operation!");
            return modelAndView;
        }

    }

    private void setCustomerForDeposit(Deposit deposit, long id) {
        deposit.setCustomer(customerService.findById(id).get());
    }

}
