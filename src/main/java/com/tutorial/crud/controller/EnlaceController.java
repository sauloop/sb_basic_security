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
import com.tutorial.crud.entity.Producto;
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

	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		List<Category> categories = categoryService.listCategories();
		model.addAttribute("categories", categories);
		return "enlace/nuevo";
	}

	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
	@PostMapping("/guardar")
	public ModelAndView crear(@RequestParam Category category, @RequestParam String title,
			@RequestParam String subtitle, String link) {
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isBlank(title)) {
			mv.setViewName("enlace/nuevo");
			mv.addObject("error", "el título no puede estar vacío");
			return mv;
		}

		if (articleService.existsByTitulo(title)) {
			mv.setViewName("enlace/nuevo");
			mv.addObject("error", "ese enlace ya existe");
			return mv;
		}
		Article enlace = new Article(title, subtitle, link, category);
		articleService.save(enlace);
		mv.setViewName("redirect:/enlace/lista");
		return mv;
	}

	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") int id) {
		if (!articleService.existsById(id)) {
			return new ModelAndView("redirect:/enlace/lista");
		}

		List<Category> categories = categoryService.listCategories();
		Article enlace = articleService.getOne(id).get();

		ModelAndView mv = new ModelAndView("enlace/editar");
		mv.addObject("categories", categories);
		mv.addObject("enlace", enlace);
		return mv;
	}

	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
	@PostMapping("actualizar")
	public ModelAndView actualizar(@RequestParam long id, @RequestParam String title, @RequestParam String subtitle,
			@RequestParam String link, @RequestParam Category category) {
		if (!articleService.existsById(id)) {
			return new ModelAndView("redirect:/enlace/lista");
		}

		ModelAndView mv = new ModelAndView();
		Article enlace = articleService.getOne(id).get();
		if (StringUtils.isBlank(title)) {
			mv.setViewName("enlace/editar");
			mv.addObject("enlace", enlace);
			mv.addObject("error", "el título no puede estar vacío");
			return mv;
		}

		if (articleService.existsByTitulo(title) && articleService.getByTitle(title).get().getId() != id) {
			mv.setViewName("enlace/editar");
			mv.addObject("error", "ese título ya existe");
			mv.addObject("enlace", enlace);
			return mv;
		}

		enlace.setTitle(title);
		enlace.setSubtitle(subtitle);
		enlace.setLink(link);
		enlace.setCategory(category);
		articleService.save(enlace);
		return new ModelAndView("redirect:/enlace/lista");
	}

	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
	@GetMapping("/borrar/{id}")
	public ModelAndView borrar(@PathVariable("id") long id) {
		if (articleService.existsById(id)) {
			articleService.delete(id);
			return new ModelAndView("redirect:/enlace/lista");
		}
		return null;
	}

	@GetMapping("/buscar")
	public String buscar(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("categories", categoryService.listCategories());

		return "enlace/buscar";
	}

	@GetMapping("/buscador")
	public String buscador(@RequestParam String name, Model model, @ModelAttribute("category") Category category) {

		if (name == "") {

			return "redirect:/enlace/buscar";

		} else {
			Category cat = categoryService.findCategoryByName(name);
			List<Article> enlaces = cat.getArticles();
			Collections.sort(enlaces);

			model.addAttribute("categories", categoryService.listCategories());
			model.addAttribute("enlaces", enlaces);

			return "enlace/buscar";
		}
	}
}
