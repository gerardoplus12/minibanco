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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.bnc.dto.ClienteDTO;
import com.app.bnc.model.entities.Cliente;
import com.app.bnc.service.ClienteService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/clientes")
public class ClienteController {
	
	//Inyección de depedencias por constructor
	private ClienteService clienteService;
	
	private static Logger logger = LogManager.getLogger(ClienteController.class);
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/crear")
	public ResponseEntity<String> guardarCliente(@Valid @RequestBody ClienteDTO cliente) {
		clienteService.guardarCliente(cliente);
		String response = "Cliente ("+cliente.getNombre()+") Registrado con Exito.";
		logger.info(response);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}

	@GetMapping("/listar")
	public ResponseEntity<List<Cliente>> listarClientes() {
		return  ResponseEntity.ok(clienteService.getAllClientes());
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<String> actualizarCliente(@PathVariable("id") Long id, @RequestBody ClienteDTO cliente) {
		cliente.setIdCliente(id);
		clienteService.actualizarCliente(cliente);
		String response = "Cliente "+cliente.getIdCliente()+" Actualizado";
		logger.info(response);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarCliente(@PathVariable("id") Long id) {
		clienteService.eliminarCliente(id);
		String response = "Cliente "+id+" eliminado exitosamente.";
		logger.info(response);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
}
