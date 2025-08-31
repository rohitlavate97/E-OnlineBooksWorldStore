package com.onlinebookstore.controller;

import com.onlinebookstore.repository.CustomerRepository;
import com.onlinebookstore.entity.Customer;
import com.onlinebookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/rest")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/createOrUpdateCustomer")
    public ResponseEntity<Customer> createOrUpdateCustomer(@RequestBody Customer customer) {
        if(customer.getId() == null) {
            Customer newCustomer = customerService.addCustomerOrUpdateCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
        } else {
            Customer updatedCustomer = customerService.addCustomerOrUpdateCustomer(customer);
            return ResponseEntity.ok(updatedCustomer);
        }
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getAllCustomers")
    public List<Customer> getAllCustomers() {
        List<Customer> list= customerRepository.findAll();
        return list;
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }
}
