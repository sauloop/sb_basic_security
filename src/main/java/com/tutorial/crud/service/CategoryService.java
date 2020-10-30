package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.tutorial.crud.entity.Article;
import com.tutorial.crud.entity.Category;
import com.tutorial.crud.repository.ICategoryRepository;

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CategoryService {

	@Autowired
	private ICategoryRepository categoryRepository;

	public List<Category> listCategories() {
		return categoryRepository.findAllByOrderByNameAsc();
	}

	public void save(Category category) {
		categoryRepository.save(category);

	}

	public Optional<Category> getOne(long id) {

		return categoryRepository.findById(id);
	}

	public void delete(long id) {
		categoryRepository.deleteById(id);
	}

//	public Category findCategoryByName(String name) {
//
//		return categoryRepository.findByName(name);
//	}

	public Optional<Category> getByName(String name) {
		return categoryRepository.findByName(name);
	}

	public boolean existsById(long id) {
		return categoryRepository.existsById(id);
	}

	public boolean existsByName(String name) {
		return categoryRepository.existsByName(name);
	}

}
