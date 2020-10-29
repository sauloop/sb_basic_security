package com.tutorial.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.Article;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface IArticleRepository extends JpaRepository<Article, Long> {
	public List<Article> findAllByOrderByIdDesc();

	boolean existsById(long id);

	boolean existsByTitle(String title);

	Optional<Article> findByTitle(String title);

}
