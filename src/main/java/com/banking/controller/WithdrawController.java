package com.banking.controller;

import com.banking.model.Customer;
import com.banking.model.Deposit;
import com.banking.model.Withdraw;
import com.banking.service.ICustomerService;
import com.banking.service.IDepositService;
import com.banking.service.IWithdrawService;
import com.banking.util.Error;
import com.banking.util.ParsingValidationUtils;
import lombok.With;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
public class WithdrawController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IWithdrawService withdrawService;

    @GetMapping("/withdraw/{id}")
    public ModelAndView showWithdrawForm(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("/withdraw");

        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);

            if (customerService.existsByIdAndDeletedFalse(validId)) {
                Withdraw newWithdraw = new Withdraw();
                setCustomerForWithdraw(newWithdraw, validId);
                modelAndView.addObject("newWithdraw", newWithdraw);
                return modelAndView;
            }

        }

        modelAndView.addObject("wrongId", "Customer ID doesn't exist!");
        return modelAndView;
    }

    @PostMapping("/withdraw/{id}")
    public ModelAndView withdraw(@PathVariable String id,
                                 @Validated @ModelAttribute("newWithdraw") Withdraw newWithdraw,
                                 BindingResult bindingResult) {

        System.out.println(newWithdraw);

        ModelAndView modelAndView = new ModelAndView("/withdraw");

        try {
            if (ParsingValidationUtils.isLongParsable(id)) {
                long validId = Long.parseLong(id);

                if (customerService.existsByIdAndDeletedFalse(validId)) {
                    setCustomerForWithdraw(newWithdraw, validId);
                    BigDecimal customerBalance = newWithdraw.getCustomer().getBalance();
//                    new Withdraw().validate(newWithdraw, bindingResult);

                    if (customerBalance.compareTo(newWithdraw.getTransactionAmount()) < 0) {
                        bindingResult.addError(Error.NOT_ENOUGH_BALANCE);
                    }

                    if (!bindingResult.hasErrors()) {
//                    if (!bindingResult.hasFieldErrors()) {
                        BigDecimal amount = newWithdraw.getTransactionAmount();

                        withdrawService.withdraw(validId, amount);
                        withdrawService.save(newWithdraw);

                        modelAndView.addObject("success", "Successful operation!");
                        setCustomerForWithdraw(newWithdraw, validId);
                        modelAndView.addObject("newWithdraw", newWithdraw);
                        return modelAndView;
                    }

                    setCustomerForWithdraw(newWithdraw, validId);
//                    modelAndView.addObject("newWithdraw", newWithdraw);
                    modelAndView.addObject("hasError", true);
                    return modelAndView;
                }
            }

            bindingResult.addError(Error.WRONG_ID);
            modelAndView.addObject("newWithdraw", new Withdraw());
            modelAndView.addObject("hasError", true);
            return modelAndView;

        } catch (Exception ex) {
            modelAndView.addObject("failure", "Failed operation!");
            return modelAndView;
        }
    }

    private void setCustomerForWithdraw(Withdraw withdraw, long id) {
        withdraw.setCustomer(customerService.findById(id).get());
    }

}
