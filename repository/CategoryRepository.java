package com.arshin.expensetracker.repository;

import com.arshin.expensetracker.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


//JPA repository for the Category entity.

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
