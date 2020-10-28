package com.tutorial.crud.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tutorial.crud.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

//	List<Category> findAllByOrderByIdDesc();
	
	List<Category> findAllByOrderByNameAsc();

	Category findByName(String name);
}
