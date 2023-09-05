package com.thenakedbookstore.services;


import com.example.newsarchivesystem.DAO.FakeDAO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {
    @Autowired
    private FakeDAO dao;

    public UserDetails GetUser(String userName){
        return dao.GetUser(userName);
    }

    public void AddUser(UserDetails u){
        dao.AddUser(u);
    }
}
