package com.app.bnc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.bnc.model.entities.Cliente;
import com.app.bnc.model.entities.Persona;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	Optional<Cliente> findByPersona(Persona persona);
	void deleteByIdCliente(Long id);
}