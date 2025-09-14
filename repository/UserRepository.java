package com.arshin.expensetracker.repository;

import com.arshin.expensetracker.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


//JPA repository for the user entity.

public interface UserRepository extends JpaRepository<User, Integer> {
    //Finds a user by their email address.
    Optional<User> findByEmail(String email);
}
