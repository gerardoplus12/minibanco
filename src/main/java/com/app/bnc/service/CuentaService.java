package com.app.bnc.service;

import java.util.List;
import java.util.Optional;

import com.app.bnc.dto.CuentaDTO;
import com.app.bnc.dto.CuentaDTORequest;
import com.app.bnc.model.entities.Cuenta;

public interface CuentaService {
	Cuenta guardarCuenta(CuentaDTO cuenta);
	List<Cuenta> getAllCuentas();
	Optional<Cuenta> getCuentaById(String nroCuenta);
	Cuenta actualizarCuenta(CuentaDTORequest cuentaActualizar);
	void eliminarCuenta(String nroCuenta); 
}