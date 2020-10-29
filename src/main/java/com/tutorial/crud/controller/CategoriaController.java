package com.tutorial.crud.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tutorial.crud.entity.Article;
import com.tutorial.crud.entity.Category;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.service.CategoryService;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/lista")
	public String listCategories(Model model) {
		List<Category> categories = categoryService.listCategories();

		model.addAttribute("categories", categories);

		return "categoria/lista";
	}

	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
	@GetMapping("/nueva")
	public String nueva(Model model) {
		return "categoria/nueva";
	}

	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
	@PostMapping("/guardar")
	public ModelAndView guardar(@RequestParam String name) {
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isBlank(name)) {
			mv.setViewName("categoria/nueva");
			mv.addObject("error", "el nombre no puede estar vacío");
			return mv;
		}

		if (categoryService.existsByName(name)) {
			mv.setViewName("categoria/nueva");
			mv.addObject("error", "esa categoría ya existe");
			return mv;
		}
		Category categoria = new Category(name);
		categoryService.save(categoria);
		mv.setViewName("redirect:/categoria/lista");
		return mv;
	}

	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
		if (!categoryService.existsById(id)) {
			return new ModelAndView("redirect:/categoria/lista");
		}

		Category category = categoryService.getOne(id).get();
		ModelAndView mv = new ModelAndView("categoria/editar");
		mv.addObject("category", category);
		return mv;
	}

	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
	@PostMapping("actualizar")
	public ModelAndView actualizar(@RequestParam long id, @RequestParam String name) {
		if (!categoryService.existsById(id)) {
			return new ModelAndView("redirect:/categoria/lista");
		}

		ModelAndView mv = new ModelAndView();
		Category category = categoryService.getOne(id).get();
		if (StringUtils.isBlank(name)) {
			mv.setViewName("categoria/editar");
			mv.addObject("category", category);
			mv.addObject("error", "el nombre no puede estar vacío");
			return mv;
		}

		if (categoryService.existsByName(name) && categoryService.getByName(name).get().getId() != id) {
			mv.setViewName("producto/editar");
			mv.addObject("error", "ese nombre ya existe");
			mv.addObject("category", category);
			return mv;
		}

		category.setName(name);
		categoryService.save(category);
		return new ModelAndView("redirect:/categoria/lista");
	}

	@PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
	@GetMapping("/borrar/{id}")
	public ModelAndView borrar(@PathVariable("id") long id) {
		if (categoryService.existsById(id)) {
			categoryService.delete(id);
			return new ModelAndView("redirect:/categoria/lista");
		}
		return null;
	}

}
