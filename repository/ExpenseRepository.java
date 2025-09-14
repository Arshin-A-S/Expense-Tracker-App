package com.arshin.expensetracker.repository;

import com.arshin.expensetracker.model.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


//JPA repository for the expense entity.
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    //Finds all expenses associated with a specific user ID
    List<Expense> findByUserId(Integer userId);
}
