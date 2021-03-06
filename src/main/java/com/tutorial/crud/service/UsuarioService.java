package com.tutorial.crud.service;

import com.tutorial.crud.entity.Usuario;
import com.tutorial.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public List<Usuario> list() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> getById(int id) {
		return usuarioRepository.findById(id);
	}

	public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
		return usuarioRepository.findByNombreUsuario(nombreUsuario);
	}

	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
	public void delete(int id) {
		usuarioRepository.deleteById(id);
	}

	public boolean existsById(int id) {
		return usuarioRepository.existsById(id);
	}

	public boolean existsByNombreusuario(String nombreUsuario) {
		return usuarioRepository.existsByNombreUsuario(nombreUsuario);
	}
}
