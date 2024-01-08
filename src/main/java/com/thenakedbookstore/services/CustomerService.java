package com.thenakedbookstore.services;


import com.thenakedbookstore.models.User;

import java.util.List;


public interface CustomerService{

    boolean createCustomer(User user);
    boolean updateCustomer(User user);
    List<User> getAllCustomers();

    User getCustomerById(String email);

    boolean deleteCustomer(String email);

}
