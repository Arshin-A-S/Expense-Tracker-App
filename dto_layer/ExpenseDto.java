package com.arshin.expensetracker.dto_layer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;


//Data Transfer Object for creating, updating, and viewing expenses
@Data
public class ExpenseDto {
    private Integer id;

    @NotNull @Positive
    private BigDecimal amount;

    @NotNull
    private Integer categoryId;

    private String categoryName;

    @NotNull
    private LocalDate date;

    private String description;
}