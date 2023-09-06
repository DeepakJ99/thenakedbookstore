package com.thenakedbookstore.services;

import com.thenakedbookstore.DAO.CustomerRepository;
import com.thenakedbookstore.models.Customer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Builder
@ToString
@AllArgsConstructor
public class CustomerServiceImpl implements  CustomerService{

    private final static String USER_NOT_FOUND = "User with email %s not found.";
    private final CustomerRepository repo;
    @Override
    public boolean createCustomer(Customer customer) {
        boolean userExists = repo.findByEmail(customer.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("Email already taken");
        }
        repo.save(customer);
        return  true;
    }

    @Override
    public boolean updateCustomer(Customer customer) throws UsernameNotFoundException{
        Customer c = getCustomerById(customer.getEmail());
        repo.deleteById(customer.getUsername());
        repo.save(customer);
        return true;

    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Customer getCustomerById(String email) throws UsernameNotFoundException {

        Optional<Customer> o = repo.findByEmail(email);
        if(o.isPresent()) return o.get();
        else throw new UsernameNotFoundException(String.format(USER_NOT_FOUND,email));
    }

    @Override
    public boolean deleteCustomer(String email) throws UsernameNotFoundException {
        Customer c = getCustomerById(email);
        repo.deleteById(email);
        return true;
    }
}
