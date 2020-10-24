package com.tutorial.crud.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tutorial.crud.entity.Rol;
import com.tutorial.crud.entity.Usuario;
import com.tutorial.crud.enums.RolNombre;
import com.tutorial.crud.service.RolService;
import com.tutorial.crud.service.UsuarioService;

@Service
public class CreateAdmin implements CommandLineRunner {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RolService rolService;

	@Override
	public void run(String... args) throws Exception {
//		Usuario usuario = new Usuario();
//		String passwordEncoded = passwordEncoder.encode("admin");
//		usuario.setNombreUsuario("admin");
//		usuario.setPassword(passwordEncoded);
//		Rol rolAdmin = rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get();
//		Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
//		List<Rol> roles = new ArrayList<>();
//		roles.add(rolAdmin);
//		roles.add(rolUser);
//		usuario.setRoles(roles);
//		usuarioService.save(usuario);
	}
}
