package com.onlinebookstore.serviceImpl;

import com.onlinebookstore.entity.Customer;
import com.onlinebookstore.repository.CustomerRepository;
import com.onlinebookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addCustomerOrUpdateCustomer(Customer customer) {
        if (customer.getId() == null) {
            customerRepository.save(customer);
        } else {
            Optional<Customer> custbyId = customerRepository.findById(customer.getId());
            if (custbyId.isPresent()) {
                customerRepository.save(customer);
            }
        }
        // If ID is null → new customer
        return null;
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> custbyId = customerRepository.findById(id);
        if (custbyId.isPresent()) {
            return custbyId.get();
        }
        return null;
    }
}
