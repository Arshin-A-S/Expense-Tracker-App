package com.arshin.expensetracker.service;

import com.arshin.expensetracker.dto_layer.ExpenseDto;
import com.arshin.expensetracker.model.entity.Category;
import com.arshin.expensetracker.model.entity.Expense;
import com.arshin.expensetracker.model.entity.User;
import com.arshin.expensetracker.repository.CategoryRepository;
import com.arshin.expensetracker.repository.ExpenseRepository;
import com.arshin.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


//Service class for managing user expenses and contains business logic for CRUD operations on expenses

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    //Adds a new expense for the currently authenticated user.
    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        User currentUser = getCurrentUser();
        Category category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Expense expense = new Expense();
        expense.setAmount(expenseDto.getAmount());
        expense.setExpenseDate(expenseDto.getDate());
        expense.setDescription(expenseDto.getDescription());
        expense.setUser(currentUser);
        expense.setCategory(category);

        Expense savedExpense = expenseRepository.save(expense);
        return mapToDto(savedExpense);
    }


    //Retrieves all expenses for the currently authenticated user.
    public List<ExpenseDto> getAllExpenses() {
        User currentUser = getCurrentUser();
        return expenseRepository.findByUserId(currentUser.getId()).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    //Deletes an expense by its ID and ensures the expense belongs to the currently authenticated user before deleting

    public void deleteExpense(Integer expenseId) {
        User currentUser = getCurrentUser();
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("You are not authorized to delete this expense");
        }
        expenseRepository.deleteById(expenseId);
    }


    //Retrieves the currently authenticated user from the security context
    private User getCurrentUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    //Maps an Expense entity to an ExpenseDto
    private ExpenseDto mapToDto(Expense expense) {
        ExpenseDto dto = new ExpenseDto();
        dto.setId(expense.getId());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getExpenseDate());
        dto.setDescription(expense.getDescription());
        dto.setCategoryId(expense.getCategory().getId());
        dto.setCategoryName(expense.getCategory().getName());
        return dto;
    }
}
