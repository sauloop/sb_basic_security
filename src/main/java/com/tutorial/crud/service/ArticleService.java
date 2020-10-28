package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Article;
import com.tutorial.crud.repository.IArticleRepository;

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ArticleService {

	@Autowired
	private IArticleRepository articleRepository;

	public List<Article> listArticles() {

		return articleRepository.findAllByOrderByIdDesc();

	}

	public void save(Article article) {
		articleRepository.save(article);

	}

	public Optional<Article> findArticleById(long id) {
		return articleRepository.findById(id);
	}

	public void deleteArticle(long id) {
		articleRepository.deleteById(id);
	}

	public boolean existsByTitulo(String title) {
		return articleRepository.existsByTitle(title);
	}

}
