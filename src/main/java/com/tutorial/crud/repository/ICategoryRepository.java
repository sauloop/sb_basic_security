package com.tutorial.crud.repository;

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tutorial.crud.entity.Category;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface ICategoryRepository extends JpaRepository<Category, Long> {

//	List<Category> findAllByOrderByIdDesc();

	List<Category> findAllByOrderByNameAsc();

	Category findByName(String name);
}
