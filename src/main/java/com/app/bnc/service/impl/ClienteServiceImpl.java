package com.app.bnc.service.impl;
  
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.app.bnc.dto.ClienteDTO;
import com.app.bnc.emun.EEstado;
import com.app.bnc.model.entities.Cliente;
import com.app.bnc.model.entities.Persona;
import com.app.bnc.repository.ClienteRepository;
import com.app.bnc.repository.PersonaRepository;
import com.app.bnc.service.ClienteService;

import lombok.AllArgsConstructor;
import com.app.bnc.exception.DataDuplicadeException;
import com.app.bnc.exception.DataNotFoundException;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {
	
	//Inyección de depedencias por constructor
	private ClienteRepository clienteRepository;
	private PersonaRepository personaRepository;
	
	private static Logger logger = LogManager.getLogger(ClienteServiceImpl.class);
	
	@Override
	public Cliente guardarCliente(ClienteDTO clienteDto){
		// TODO Auto-generated method stub
		Optional<Persona> personaOptional = personaRepository.findById(clienteDto.getIdPersona());
		Persona objPersona = new Persona();
		
		if(personaOptional.isPresent()) {
			objPersona =personaOptional.get(); 
			boolean personInClteFound = clienteRepository.findByPersona(objPersona).isPresent();
    		if (personInClteFound) {
    			String mensaje = "Error de Validacion en Cliente: Un Cliente con la identificación ("+clienteDto.getIdPersona()+") ya existe en la entidad Cliente.";
    			logger.info(mensaje);
    			throw new DataDuplicadeException(mensaje);
    		}
		}
		//Creamos la Persona con el Id y Nombre especificado
		objPersona.setId(clienteDto.getIdPersona());
		objPersona.setNombre(clienteDto.getNombre());
		objPersona.setDireccion(clienteDto.getDireccion());
		objPersona.setEdad(clienteDto.getEdad());
		objPersona.setGenero(clienteDto.getGenero());
		objPersona.setTelefono(clienteDto.getTelefono());
		personaRepository.save(objPersona);
		Cliente newCliente = new Cliente(objPersona,clienteDto.getContrasena(),clienteDto.getEstado());
		return clienteRepository.save(newCliente);
	}

	@Override
	public List<Cliente> getAllClientes() {
		// TODO Auto-generated method stub
		return clienteRepository.findAll();
	}

	@Override
	public Optional<Cliente> getClienteById(Long id) {
		// TODO Auto-generated method stub
		return clienteRepository.findById(id);
	}

	@Override
	public Cliente actualizarCliente(ClienteDTO clienteDto) {
		// TODO Auto-generated method stub
		Optional<Cliente> clienteActual = clienteRepository.findById(clienteDto.getIdCliente());
		if(!clienteActual.isPresent()){
			String mensaje = "Error de Validacion en Cliente: no existe el registro a actualizar: "+clienteDto.getIdCliente() ;
			logger.info(mensaje);
			throw new DataNotFoundException(mensaje);	
		}
		clienteActual.get().setContrasena(clienteDto.getContrasena());
		clienteActual.get().setEstado(clienteDto.getEstado());
		Persona personaAEditar = clienteActual.get().getPersona();
		personaAEditar.setDireccion(clienteDto.getDireccion());
		personaAEditar.setEdad(clienteDto.getEdad());
		personaAEditar.setGenero(clienteDto.getGenero());
		personaAEditar.setNombre(clienteDto.getNombre());
		personaAEditar.setTelefono(clienteDto.getTelefono());
		personaRepository.save(personaAEditar);
		return clienteRepository.save(clienteActual.get());
	}

	@Override
	public void eliminarCliente(Long id) {
		// TODO Auto-generated method stub
		Optional<Cliente> clienteActual = clienteRepository.findById(id);
		if(!clienteActual.isPresent()){
			String mensaje = "Error de Validacion en Cliente: no existe el registro a eliminar: "+id.toString() ;
			logger.info(mensaje);
			throw new DataNotFoundException(mensaje);	
		}
		//Eliminacion Logica por campo Estado
		clienteActual.get().setEstado(EEstado.INACTIVO.toString());
		clienteRepository.save(clienteActual.get());
	}
}