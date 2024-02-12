package com.app.bnc.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bnc.dto.CuentaDTO;
import com.app.bnc.dto.CuentaDTORequest;
import com.app.bnc.model.entities.Cuenta;
import com.app.bnc.service.CuentaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/cuentas")
public class CuentaController {
	
	//Inyección de depedencias por constructor
	private CuentaService cuentaService;
	
	private static Logger logger = LogManager.getLogger(CuentaController.class);
	
	@PostMapping("/crear") 
	public ResponseEntity<String> guardarCuenta(@RequestBody CuentaDTO cuenta) {
		cuentaService.guardarCuenta(cuenta);
		String response = "Cuenta ("+cuenta.getNroCuenta()+") Registrado con Exito.";
		logger.info(response);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	} 
	
	@GetMapping("/listar") 
	public ResponseEntity<List<Cuenta>> listarCuentas() {
		return  ResponseEntity.ok(cuentaService.getAllCuentas());
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<String> actualizarCuenta(@PathVariable("id") String id, @RequestBody CuentaDTORequest cuenta) {
		cuenta.setNroCuenta(id);
		cuentaService.actualizarCuenta(cuenta);
		String response = "Cuenta "+cuenta.getNroCuenta()+" Actualizado";
		logger.info(response);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarCuenta(@PathVariable("id") String id) {
		cuentaService.eliminarCuenta(id);
		String response = "Cuenta "+id+" eliminada exitosamente.";
		logger.info(response);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
}
