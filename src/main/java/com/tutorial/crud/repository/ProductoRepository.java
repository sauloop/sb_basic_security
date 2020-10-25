package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Producto;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	Optional<Producto> findByNombre(String nombre);

	boolean existsByNombre(String nombre);
}
