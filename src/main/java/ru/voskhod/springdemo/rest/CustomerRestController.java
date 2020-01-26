package ru.voskhod.springdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.voskhod.springdemo.entity.Customer;
import ru.voskhod.springdemo.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    // autowire CustomerService
    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    // add mapping for GET /customers
    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    // add mapping for GET /customers/{customerId}
    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable int customerId) {
        Customer customer = customerService.getCustomer(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id: " + customerId + " not found");
        }

        return customer;
    }

    // add mapping for POST /customers
    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer customer) {

        // force INSERT instead of UPDATE
        customer.setId(0);

        customerService.saveCustomer(customer);

        return customer;
    }

    // add mapping for PUT /customers
    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return customer;
    }

    // add mapping for DELETE /customers
    @DeleteMapping("/customers/{customerId}")
    public Customer deleteCustomer(@PathVariable int customerId) {

        Customer customer = customerService.getCustomer(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id: " + customerId + " not found");
        }

        customerService.deleteCustomer(customerId);
        return customer;
    }


}
