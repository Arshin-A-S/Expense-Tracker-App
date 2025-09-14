package com.arshin.expensetracker.controller;

import com.arshin.expensetracker.dto_layer.ExpenseDto;
import com.arshin.expensetracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//REST controller for managing expense operations
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@Tag(name = "2. Expense Management", description = "Endpoints for managing user expenses (Protected)")
@SecurityRequirement(name = "bearerAuth")
public class ExpenseController {

    private final ExpenseService expenseService;

    //Endpoint to add a new expense
    @Operation(summary = "Add a new expense", description = "Creates a new expense record for the authenticated user. Requires Bearer Token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expense created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid")
    })
    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        ExpenseDto newExpense = expenseService.addExpense(expenseDto);
        return new ResponseEntity<>(newExpense, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all expenses for a user", description = "Retrieves a list of all expenses recorded by the authenticated user. Requires Bearer Token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of expenses"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid")
    })
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }


    @Operation(summary = "Delete an expense", description = "Deletes a specific expense by its ID. User can only delete their own expenses. Requires Bearer Token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User trying to delete another user's expense"),
            @ApiResponse(responseCode = "404", description = "Not Found - Expense with the given ID does not exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(
            @Parameter(description = "ID of the expense to be deleted") @PathVariable("id") Integer expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok("Expense deleted successfully.");
    }
}
