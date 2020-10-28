package com.tutorial.crud.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.tutorial.crud.entity.Category;
import com.tutorial.crud.repository.ICategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private ICategoryRepository categoryRepository;

	public Iterable<Category> listCategories() {

		return categoryRepository.findAll();
	}

	public Page<Category> listCategories(Pageable categoryPageable) {
		return categoryRepository.findAllByOrderByIdDesc(categoryPageable);
	}

	public void addCategory(Category category) {
		categoryRepository.save(category);

	}

	public Optional<Category> findCategoryById(long id) {

		return categoryRepository.findById(id);
	}

	public void deleteCategory(long id) {
		categoryRepository.deleteById(id);
	}

	public Category findCategoryByName(String name) {

		return categoryRepository.findByName(name);
	}

}
