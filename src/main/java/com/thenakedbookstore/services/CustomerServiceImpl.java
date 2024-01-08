package com.thenakedbookstore.services;

import com.thenakedbookstore.DAO.UserRepository;
import com.thenakedbookstore.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Builder
@ToString
@AllArgsConstructor
public class CustomerServiceImpl implements  CustomerService{

    private final static String USER_NOT_FOUND = "User with email %s not found.";
    private final UserRepository repo;
    @Override
    public boolean createCustomer(User user) {
        boolean userExists = repo.findByEmail(user.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("Email already taken");
        }
        repo.save(user);
        return  true;
    }

    @Override
    public boolean updateCustomer(User user) throws UsernameNotFoundException{
        User c = getCustomerById(user.getEmail());
        repo.deleteById(user.getUsername());
        repo.save(user);
        return true;

    }

    @Override
    public List<User> getAllCustomers() {
        return null;
    }

    @Override
    public User getCustomerById(String email) throws UsernameNotFoundException {

        Optional<User> o = repo.findByEmail(email);
        if(o.isPresent()) return o.get();
        else throw new UsernameNotFoundException(String.format(USER_NOT_FOUND,email));
    }

    @Override
    public boolean deleteCustomer(String email) throws UsernameNotFoundException {
        User c = getCustomerById(email);
        repo.deleteById(email);
        return true;
    }
}
