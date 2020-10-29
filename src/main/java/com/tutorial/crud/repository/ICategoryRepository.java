package com.tutorial.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.Article;
import com.tutorial.crud.entity.Category;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface ICategoryRepository extends JpaRepository<Category, Long> {

//	List<Category> findAllByOrderByIdDesc();

	boolean existsById(long id);

	boolean existsByName(String name);

	List<Category> findAllByOrderByNameAsc();

//	Category findByName(String name);

	Optional<Category> findByName(String name);
}
