package com.app.bnc.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.app.bnc.model.entities.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, String>{
}