package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.Category;
import ru.nightmare.diplomny.entity.TestAnswerReward;
import ru.nightmare.diplomny.repository.CategoryRepository;
import ru.nightmare.diplomny.repository.TestAnswerRewardRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // Create
    public Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    // Read
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(int id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    // Update
    public Category updateCategory(int id, String name) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(name);
        return categoryRepository.save(category);
    }

    // Delete
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}