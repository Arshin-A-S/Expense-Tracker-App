package com.arshin.expensetracker.dto_layer;

import lombok.AllArgsConstructor;
import lombok.Data;


//Data Transfer Object for returning a JWT upon successful authentication
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
