package com.arshin.expensetracker.service;

import com.arshin.expensetracker.dto_layer.CategoryDto;
import com.arshin.expensetracker.model.entity.Category;
import com.arshin.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


//Service class for managing expense categories
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    //Retrieves all available categories
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    //Creates a new category
    public CategoryDto createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    //Maps a Category entity to a CategoryDto
    private CategoryDto mapToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}
