package com.banking.controller;

import com.banking.dto.CustomerDTO;
import com.banking.service.ICustomerService;
import com.banking.util.ParsingValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("")
    public ModelAndView showCustomerList() {
        ModelAndView modelAndView = new ModelAndView("/list");
        List<CustomerDTO> customerList = customerService.findAllDTO();
        modelAndView.addObject("customerList", customerList);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/create");
        CustomerDTO newCustomerDTO = new CustomerDTO();
        modelAndView.addObject("customerDTO", newCustomerDTO);
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView crateCustomer(@Validated @ModelAttribute("customerDTO") CustomerDTO customerDTO,
                                      BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/create");

        String name = customerDTO.getFullName().trim();
        String phone = customerDTO.getPhone().trim();
        String email = customerDTO.getEmail().trim().toLowerCase();
        String address = customerDTO.getAddress().trim();

        if (customerService.existsByPhone(phone)) {
            bindingResult.addError(new ObjectError("phoneExists", "Phone number has existed!"));
        }

        if (customerService.existsByEmail(email)) {
            bindingResult.addError(new ObjectError("emailExists", "Email address has existed!"));
        }

        if (!bindingResult.hasErrors()) {
            if (customerService.addNewCustomer(name, email, phone, address)) {
                modelAndView.addObject("success", "Successful operation!");
            } else {
                modelAndView.addObject("failure", "Failed operation!");
            }
            modelAndView.addObject("customerDTO", new CustomerDTO());
        } else {
            modelAndView.addObject("hasError", true);
        }

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("/edit");
        return dispatchRequest(modelAndView, id);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updateCustomer(@PathVariable String id,
                                       @Validated @ModelAttribute("customerDTO") CustomerDTO currentCustomer,
                                       BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/edit");

        String name = currentCustomer.getFullName().trim();
        String phone = currentCustomer.getPhone().trim();
        String email = currentCustomer.getEmail().trim().toLowerCase();
        String address = currentCustomer.getAddress().trim();

        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);
            if (customerService.existsByIdAndDeletedFalse(validId)) {

                if (customerService.existsByPhoneAndIdIsNot(phone, validId)) {
                    bindingResult.addError(new ObjectError("phoneExists", "Phone number has existed!"));
                }

                if (customerService.existsByEmailAndIdIsNot(email, validId)) {
                    bindingResult.addError(new ObjectError("emailExists", "Email address has existed!"));
                }

                if (!bindingResult.hasErrors()) {
                    if (customerService.updateCustomer(validId, name, email, phone, address)) {
                        modelAndView.addObject("success", "Successful operation!");
                    } else {
                        modelAndView.addObject("failure", "Failed operation!");
                    }
                } else {
                    modelAndView.addObject("hasError", true);
                }

                modelAndView.addObject("customerDTO", currentCustomer);
                return modelAndView;
            }
        }

        modelAndView.addObject("customerDTO", new CustomerDTO());
        modelAndView.addObject("wrongId", "Customer ID doesn't exist!");
        return modelAndView;

    }

    @GetMapping("/suspend/{id}")
    public ModelAndView showSuspendForm(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("/suspend");
        return dispatchRequest(modelAndView, id);
    }

    @PostMapping("/suspend/{id}")
    public ModelAndView suspendCustomer(@PathVariable String id,
                                        @ModelAttribute("customerDTO") CustomerDTO currentCustomer) {
        ModelAndView modelAndView = new ModelAndView("/suspend");

        if(ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);
            if (customerService.existsByIdAndDeletedFalse(validId)) {
                customerService.suspendCustomer(validId);
                modelAndView.addObject("success", "Successful operation!");
                modelAndView.addObject("customerDTO", currentCustomer);
                return modelAndView;
            }
        }

        modelAndView.addObject("customerDTO", new CustomerDTO());
        modelAndView.addObject("wrongId", "Customer ID doesn't exist!");

        return modelAndView;
    }

    private ModelAndView dispatchRequest(ModelAndView modelAndView, String id) {
        if (ParsingValidationUtils.isLongParsable(id)) {
            long validId = Long.parseLong(id);
            if (customerService.existsByIdAndDeletedFalse(validId)) {
                CustomerDTO currentCustomer = customerService.findCustomerDTOById(validId);
                modelAndView.addObject("customerDTO", currentCustomer);
                return modelAndView;
            }
        }

        modelAndView.addObject("customerDTO", new CustomerDTO());
        modelAndView.addObject("wrongId","Customer ID doesn't exist!");
        return modelAndView;
    }
}