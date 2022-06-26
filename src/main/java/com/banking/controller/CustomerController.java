package com.banking.controller;

import com.banking.dto.CustomerDTO;
import com.banking.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.*;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @RequestMapping(value = "")
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
        modelAndView.addObject("newCustomerDTO", newCustomerDTO);
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView crateCustomer(@Valid @ModelAttribute("newCustomer") CustomerDTO newCustomer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/create");
        String name = newCustomer.getFullName();
        String phone = newCustomer.getPhone();
        String email = newCustomer.getEmail();
        String address = newCustomer.getAddress();

//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        Validator validator = validatorFactory.getValidator();
//        Set<ConstraintViolation<CustomerDTO>> constraintViolations = validator.validate(newCustomer);

        if (!bindingResult.hasErrors()) {
            if (customerService.addNewCustomer(name, email, phone, address)) {
                modelAndView.addObject("success", "Successful operation!");
            } else {
                modelAndView.addObject("failure", "Failed operation!");
            }
        }

//        modelAndView.addObject("errors", constraintViolations);
        modelAndView.addObject("newCustomerDTO", new CustomerDTO());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("/edit");
        if (customerService.existsByIdAndDeletedFalse(id)) {
            CustomerDTO currentCustomer = customerService.findCustomerDTOById(id);
            modelAndView.addObject("currentCustomer", currentCustomer);
        }
        else {
            modelAndView.addObject("currentCustomer", new CustomerDTO());
            modelAndView.addObject("error", "Customer ID doesn't exist!");
        }
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updateCustomer(@PathVariable long id, @ModelAttribute CustomerDTO currentCustomer) {
        ModelAndView modelAndView = new ModelAndView("/edit");

        if (customerService.existsByIdAndDeletedFalse(id)) {
            String name = currentCustomer.getFullName();
            String phone = currentCustomer.getPhone();
            String email = currentCustomer.getEmail();
            String address = currentCustomer.getAddress();
            if (customerService.updateCustomer(id, name, email, phone, address)) {
                modelAndView.addObject("success", "Successful operation!");
            }
            else {
                modelAndView.addObject("failure", "Failed operation!");
            }
            modelAndView.addObject("currentCustomer", currentCustomer);
        }
        else {
            modelAndView.addObject("currentCustomer", new CustomerDTO());
            modelAndView.addObject("error", "Customer ID doesn't exist!");
        }
        return modelAndView;
    }

    @GetMapping("/suspend/{id}")
    public ModelAndView showSuspendForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("/suspend");
        if (customerService.existsByIdAndDeletedFalse(id)) {
        CustomerDTO currentCustomer = customerService.findCustomerDTOById(id);
        modelAndView.addObject("currentCustomer", currentCustomer);
        }
        else {
            modelAndView.addObject("currentCustomer", new CustomerDTO());
            modelAndView.addObject("error", "Customer ID doesn't exist!");
        }
        return modelAndView;
    }

    @PostMapping("/suspend/{id}")
    public ModelAndView suspendCustomer(@PathVariable long id, @ModelAttribute CustomerDTO currentCustomer) {
        ModelAndView modelAndView = new ModelAndView("/suspend");
        if (customerService.existsByIdAndDeletedFalse(id)) {
            customerService.suspendCustomer(id);
            modelAndView.addObject("success", "Successful operation!");
            modelAndView.addObject("currentCustomer", currentCustomer);
        }
        else {
            modelAndView.addObject("currentCustomer", new CustomerDTO());
            modelAndView.addObject("error", "Customer ID doesn't exist!");
        }
        return modelAndView;
    }



}