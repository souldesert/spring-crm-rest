package ru.voskhod.springdemo.service;

import ru.voskhod.springdemo.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();

    void saveCustomer(Customer customer);

    Customer getCustomer(int id);

    void deleteCustomer(int id);

    List<Customer> findCustomersByName(String searchName);
}
