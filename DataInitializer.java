package com.arshin.expensetracker;

import com.arshin.expensetracker.model.entity.Category;
import com.arshin.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This component runs on startup to ensure default categories exist in the database.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if categories already exist to prevent creating duplicates on every restart
        if (categoryRepository.count() == 0) {
            System.out.println(">>> No categories found. Seeding default categories... <<<");

            // Create a list of default categories
            Category food = new Category();
            food.setName("Food");

            Category transport = new Category();
            transport.setName("Transport");

            Category utilities = new Category();
            utilities.setName("Utilities");

            Category entertainment = new Category();
            entertainment.setName("Entertainment");

            Category other = new Category();
            other.setName("Other");

            // Save all categories to the database in one operation
            categoryRepository.saveAll(List.of(food, transport, utilities, entertainment, other));

            System.out.println("Default categories seeded successfully!");
        }
    }
}