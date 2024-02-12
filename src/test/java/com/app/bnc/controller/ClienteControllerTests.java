package com.app.bnc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.app.bnc.dto.ClienteDTO;
import com.app.bnc.emun.EEstado;
import com.app.bnc.emun.EGenero;
import com.app.bnc.model.entities.Cliente;
import com.app.bnc.service.ClienteService;
import com.app.bnc.service.CuentaService;
import com.app.bnc.service.MovimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class ClienteControllerTests {
	
	@Autowired
	private MockMvc mockMvc; 

	@MockBean
	ClienteService clienteService;
	
	@MockBean
	CuentaService cuentaService;
	
	@MockBean
	MovimientoService movimientoService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@DisplayName("Test 1: Controller Guardar Cliente OK")
    @Test
    void testGuardarClienteOK() throws Exception {
        //Given
        ClienteDTO cliDto = new ClienteDTO(1L, "16532539","Gerardo", "CALLE 21","04123029522",EGenero.MASCULINO,40,"123456",EEstado.ACTIVO.toString());

        given(clienteService.guardarCliente(any(ClienteDTO.class)))
        .willReturn(any(Cliente.class));
        
       //When
        ResultActions response = mockMvc.perform(post("/api/clientes/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliDto)));

        //Then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("Cliente ("+cliDto.getNombre()+") Registrado con Exito."));
    }
}
