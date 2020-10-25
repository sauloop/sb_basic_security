package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Rol;
import com.tutorial.crud.enums.RolNombre;
import com.tutorial.crud.repository.RolRepository;

@Service
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RolService {

	@Autowired
	RolRepository rolRepository;

	public List<Rol> list() {
		return rolRepository.findAll();
	}

	public void save(Rol rol) {
		rolRepository.save(rol);
	}

	public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
		return rolRepository.findByRolNombre(rolNombre);
	}

	public boolean existsByRolNombre(RolNombre rolNombre) {
		return rolRepository.existsByRolNombre(rolNombre);
	}
}
