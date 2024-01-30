package com.thenakedbookstore.DAO;

import com.thenakedbookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = """
      select u from User u where u.email = :email
      """)
    Optional<User> findByEmail(String email);
}
