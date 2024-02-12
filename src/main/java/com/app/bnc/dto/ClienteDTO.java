package com.app.bnc.dto;

import java.io.Serializable;

import com.app.bnc.constraints.ValueOfEnum;
import com.app.bnc.emun.EEstado;
import com.app.bnc.emun.EGenero;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size; 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Long idCliente;
	
	@NotBlank (message = "Identificación no puede ser blanco-vacio-nulo")
	@Size(max = 20)
    private String idPersona;
	
	@NotBlank (message = "Nombre no puede ser blanco-vacio-nulo")
	@Size(max = 50)
    private String nombre;
	
	@NotBlank (message = "Dirección no puede ser blanco-vacio-nulo")
   	@Size(max = 150)
    private String direccion;
    
   	@Size(max = 20)
    private String telefono;
	
	@Enumerated(EnumType.STRING)
	private EGenero genero;
	
    private Integer edad;
    
	@NotBlank (message = "Password no puede ser vacio")
	@Size(max = 60)
    private String contrasena;
	
	@ValueOfEnum(enumClass = EEstado.class)
	@Size(max = 20)
    private String estado;
}
