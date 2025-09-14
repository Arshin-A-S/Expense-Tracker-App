package com.arshin.expensetracker.dto_layer;

import lombok.Data;


//Data Transfer Object for Category operations
@Data
public class CategoryDto {
    private Integer id;
    private String name;
}