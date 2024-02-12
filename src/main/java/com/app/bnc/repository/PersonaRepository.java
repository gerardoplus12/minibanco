package com.app.bnc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.bnc.model.entities.Persona;

public interface PersonaRepository extends JpaRepository<Persona, String>{
	Optional<Persona> findById(String idPersona);
}