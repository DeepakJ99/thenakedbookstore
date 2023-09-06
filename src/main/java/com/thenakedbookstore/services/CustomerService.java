package com.thenakedbookstore.services;


import com.thenakedbookstore.DAO.CustomerRepository;
import com.thenakedbookstore.models.Customer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;



public interface CustomerService{

    boolean createCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    List<Customer> getAllCustomers();

    Customer getCustomerById(String email);

    boolean deleteCustomer(String email);

}
