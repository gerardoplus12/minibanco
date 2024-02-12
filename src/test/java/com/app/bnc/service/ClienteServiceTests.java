package com.app.bnc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.bnc.dto.ClienteDTO;
import com.app.bnc.emun.EEstado;
import com.app.bnc.emun.EGenero;
import com.app.bnc.model.entities.Cliente;
import com.app.bnc.model.entities.Persona;
import com.app.bnc.repository.ClienteRepository;
import com.app.bnc.repository.PersonaRepository;
import com.app.bnc.service.impl.ClienteServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTests {

	@Autowired
	ClienteServiceImpl clienteService;
	
	private ClienteDTO clienteDto;
	private Persona objPersona;
	
	@BeforeEach
	void setup() {
		clienteDto = new ClienteDTO(null, "","", "CALLE 21","04123029522",EGenero.MASCULINO,40,"123456",EEstado.ACTIVO.toString());
		objPersona = new Persona();
	    objPersona.setId(clienteDto.getIdPersona());
	    objPersona.setNombre(clienteDto.getNombre());
	    objPersona.setDireccion(clienteDto.getDireccion());
	    objPersona.setEdad(clienteDto.getEdad());
	    objPersona.setGenero(clienteDto.getGenero());
	    objPersona.setTelefono(clienteDto.getTelefono());
	}
	
	@DisplayName("1) Test Unit: Guadar un cliente OK")
	@Test
	void testGuardarClienteOK() {
	    
		//Given
	    ClienteRepository clienteRepositoryMock = mock(ClienteRepository.class);
	    PersonaRepository personaRepositoryMock = mock(PersonaRepository.class);
	    
		Cliente newCliente = new Cliente(objPersona,clienteDto.getContrasena(),clienteDto.getEstado());
	    when(personaRepositoryMock.findById(clienteDto.getIdPersona())).thenReturn(Optional.empty());
	    when(personaRepositoryMock.save(any(Persona.class))).thenReturn(objPersona);
	    when(clienteRepositoryMock.save(any(Cliente.class))).thenReturn(newCliente);
	    ClienteServiceImpl clienteService = new ClienteServiceImpl(clienteRepositoryMock, personaRepositoryMock);

	    // When
	    Cliente clienteGuardado = clienteService.guardarCliente(clienteDto);

	    // Then
	    assertNotNull(clienteGuardado);
	    assertEquals(clienteDto.getNombre(), clienteGuardado.getPersona().getNombre());
	    verify(personaRepositoryMock).save(any(Persona.class));
	    verify(clienteRepositoryMock).save(any(Cliente.class));
	}
}
