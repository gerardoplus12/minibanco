package com.app.bnc.service;

import java.util.List;
import java.util.Optional;

import com.app.bnc.dto.ClienteDTO;
import com.app.bnc.model.entities.Cliente;

public interface ClienteService {
	Cliente guardarCliente(ClienteDTO cliente);
	List<Cliente> getAllClientes();
	Optional<Cliente> getClienteById(Long id);
	Cliente actualizarCliente(ClienteDTO clienteActualizar);
	void eliminarCliente(Long id);
}