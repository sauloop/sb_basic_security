package com.tutorial.crud.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.entity.Rol;
import com.tutorial.crud.entity.Usuario;
import com.tutorial.crud.enums.RolNombre;
import com.tutorial.crud.service.RolService;
import com.tutorial.crud.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	RolService rolService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/testeditar")
	public String testeditar(Model model) {

		Usuario usuario = usuarioService.getById(3).get();
		model.addAttribute("usuario", usuario);
		model.addAttribute("noEditor", true);
		return "testeditar";
	}

	@PostMapping("/testactualizar")
	public String testactualizar(@RequestParam String rol, Model model) {

		model.addAttribute("rol", rol);

		return "testactualizar";
	}

	@GetMapping("/registro")
	public String registro() {
		return "registro";
	}

	@PostMapping("/registrar")
	public ModelAndView registrar(String nombreUsuario, String password) {
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isBlank(nombreUsuario)) {
			mv.setViewName("registro");
			mv.addObject("error", "el nombre no puede estar vacío");
			return mv;
		}
		if (StringUtils.isBlank(password)) {
			mv.setViewName("registro");
			mv.addObject("error", "la contraseña no puede estar vacía");
			return mv;
		}
		if (usuarioService.existsByNombreusuario(nombreUsuario)) {
			mv.setViewName("registro");
			mv.addObject("error", "ese nombre de usuario ya existe");
			return mv;
		}
		Usuario usuario = new Usuario();
		usuario.setNombreUsuario(nombreUsuario);
		usuario.setPassword(passwordEncoder.encode(password));
		Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
//		Rol rolEditor = rolService.getByRolNombre(RolNombre.ROLE_EDITOR).get();
//		Set<Rol> roles = new HashSet<>();
		List<Rol> roles = new ArrayList<>();
		roles.add(rolUser);
//		roles.add(rolEditor);
		usuario.setRoles(roles);
		usuarioService.save(usuario);
		mv.setViewName("login");
		mv.addObject("registroOK", "Cuenta creada, " + usuario.getNombreUsuario() + ", ya puedes iniciar sesión");
		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/lista")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usuario/lista");
		List<Usuario> usuarios = usuarioService.list();
		List<Usuario> usrFiltrados = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			boolean isEditor = false;
			for (Rol rol : usuario.getRoles()) {
				if (rol.getRolNombre().equals(RolNombre.ROLE_EDITOR)) {
					isEditor = true;
				}
			}
			if (!isEditor) {
				usrFiltrados.add(usuario);
			}
		}
		mv.addObject("usuarios", usrFiltrados);
		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/listaeditores")
	public ModelAndView editores() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usuario/listaeditores");
		List<Usuario> usuarios = usuarioService.list();
		List<Usuario> usrFiltrados = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			boolean isEditor = false;
			for (Rol rol : usuario.getRoles()) {
				if (rol.getRolNombre().equals(RolNombre.ROLE_EDITOR)) {
					isEditor = true;
				}
			}
			if (isEditor) {
				usrFiltrados.add(usuario);
			}
		}
		mv.addObject("usuarios", usrFiltrados);
		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") int id) {
		if (!usuarioService.existsById(id))
			return new ModelAndView("redirect:/usuario/lista");
		Usuario usuario = usuarioService.getById(id).get();
		ModelAndView mv = new ModelAndView("usuario/editar");
		mv.addObject("usuario", usuario);
//		mv.addObject("noEditor", true);
		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/modificareditor/{id}")
	public ModelAndView modificareditor(@PathVariable("id") int id) {
		if (!usuarioService.existsById(id))
			return new ModelAndView("redirect:/usuario/lista");
		Usuario usuario = usuarioService.getById(id).get();
		ModelAndView mv = new ModelAndView("usuario/modificareditor");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/actualizar")
	public ModelAndView actualizar(@RequestParam int id, @RequestParam String rol) {
		if (!usuarioService.existsById(id))
			return new ModelAndView("redirect:/producto/lista");
		Usuario usuario = usuarioService.getById(id).get();

		if (rol.equals("ROLE_EDITOR")) {
			Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
			Rol rolEditor = rolService.getByRolNombre(RolNombre.ROLE_EDITOR).get();

			List<Rol> roles = new ArrayList<>();
			roles.add(rolUser);
			roles.add(rolEditor);
			usuario.setRoles(roles);
			usuarioService.save(usuario);

		}
//		usuario.setNombreUsuario(nombre);
		usuarioService.save(usuario);
		return new ModelAndView("redirect:/usuario/lista");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/actualizareditor")
	public ModelAndView actualizareditor(@RequestParam int id, @RequestParam String rol) {
		if (!usuarioService.existsById(id))
			return new ModelAndView("redirect:/producto/lista");
		Usuario usuario = usuarioService.getById(id).get();

		if (rol.equals("ROLE_USUARIO")) {
			Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();

			List<Rol> roles = new ArrayList<>();
			roles.add(rolUser);
			usuario.setRoles(roles);
			usuarioService.save(usuario);

		}
//		usuario.setNombreUsuario(nombre);
		usuarioService.save(usuario);
		return new ModelAndView("redirect:/usuario/listaeditores");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/borrar/{id}")
	public ModelAndView borrar(@PathVariable("id") int id) {
		if (usuarioService.existsById(id)) {
			usuarioService.delete(id);
			return new ModelAndView("redirect:/usuario/lista");
		}
		return null;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/borrareditor/{id}")
	public ModelAndView borrareditor(@PathVariable("id") int id) {
		if (usuarioService.existsById(id)) {
			usuarioService.delete(id);
			return new ModelAndView("redirect:/usuario/listaeditores");
		}
		return null;
	}
}
