package com.onlinebookstore.service;

import com.onlinebookstore.entity.Customer;

public interface CustomerService {
    public Customer addCustomerOrUpdateCustomer(Customer customer);
    public Customer getCustomerById(Long id);
}
