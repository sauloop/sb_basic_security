package com.tutorial.crud.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tutorial.crud.entity.Article;
import com.tutorial.crud.entity.Category;
import com.tutorial.crud.service.ArticleService;
import com.tutorial.crud.service.CategoryService;

@Controller
@RequestMapping("/enlace")
public class EnlaceController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/lista")
	public String listArticles(Model model) {
		List<Article> enlaces = articleService.listArticles();

		model.addAttribute("enlaces", enlaces);

		return "enlace/lista";
	}

//	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
//	@GetMapping("/nuevo")
//	public String nuevo(Model model) {
//		List<Category> categories = categoryService.listCategories();
//		model.addAttribute("categories", categories);
//		return "enlace/nuevo";
//	}

//	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
//	@PostMapping("/guardar")
//	public ModelAndView crear(@RequestParam Category category, @RequestParam String title,
//			@RequestParam String subtitle, String link) {
//		ModelAndView mv = new ModelAndView();
//		if (StringUtils.isBlank(title)) {
//			mv.setViewName("enlace/nuevo");
//			mv.addObject("error", "el título no puede estar vacío");
//			return mv;
//		}
//
//		if (articleService.existsByTitulo(title)) {
//			mv.setViewName("enlace/nuevo");
//			mv.addObject("error", "ese enlace ya existe");
//			return mv;
//		}
//		Article enlace = new Article(title, subtitle, link, category);
//		articleService.save(enlace);
//		mv.setViewName("redirect:/enlace/lista");
//		return mv;
//	}

//	@GetMapping("/formsearch")
//	public String formSearch(Model model) {
//		model.addAttribute("category", new Category());
//		model.addAttribute("categories", categoryService.listCategories());
//
//		return "formSearch";
//	}
//
//	@GetMapping("/search")
//	public String searchByCategory(@RequestParam String name, Model model,
//			@ModelAttribute("category") Category category) {
//
//		if (name == "") {
//
//			return "redirect:/formsearch";
//
//		} else {
//			Category cat = categoryService.findCategoryByName(name);
//			List<Article> articles = cat.getArticles();
//			Collections.sort(articles);
//
//			model.addAttribute("categories", categoryService.listCategories());
//			model.addAttribute("articles", articles);
//
//			return "formSearch";
//		}
//	}
//
//	@GetMapping("/trueknic")
//	public String trueknic() {
//		return "trueknic";
//	}
//
//	@GetMapping("/admin")
//	public String admin() {
//		return "admin";
//	}
//
//	@GetMapping("/admin/articles/adminarticles")
//	public String adminArticles(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
//
//		Pageable articlePageable = PageRequest.of(page, 10);
//
//		Page<Article> articles = articleService.listArticles(articlePageable);
//
//		RenderizadorPaginas<Article> renderizadorPaginas = new RenderizadorPaginas<Article>("", articles);
//
//		model.addAttribute("renpag", renderizadorPaginas);
//		model.addAttribute("articles", articles);
//
//		return "adminArticles";
//	}
//
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}
//
//	@GetMapping("/admin/articles/formarticle")
//	public String formArticle(Model model, @RequestParam(name = "id", required = true) long id) {
//
//		Article article = new Article();
//
//		Optional<Article> artOp = articleService.findArticleById(id);
//
//		if (artOp.isPresent()) {
//
//			article = artOp.get();
//		}
//
//		else {
//
//			Date date = new Date();
//
//			article.setDay(date);
//		}
//
//		model.addAttribute("article", article);
//		model.addAttribute("categories", categoryService.listCategories());
//		return "formArticle";
//	}
//
//	@PostMapping("/admin/articles/addarticle")
//	public String addArticle(@Valid Article article, BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			model.addAttribute("article", article);
//			return "formArticle";
//		}
//
//		articleService.addArticle(article);
//
//		return "redirect:/admin/articles/adminarticles";
//	}
//
//	@GetMapping("/admin/articles/delete/{id}")
//	public String deleteArticle(@PathVariable("id") long id) {
//		articleService.deleteArticle(id);
//
//		return "redirect:/admin/articles/adminarticles";
//	}
}
