package com.app.bnc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.app.bnc.constraints.ValueOfEnum;
import com.app.bnc.emun.EEstado;

import jakarta.validation.constraints.NotBlank;
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
public class CuentaDTORequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank (message = "Nro cuenta no puede ser blanco-vacio-nulo")
    private String nroCuenta;
	
	@NotBlank (message = "Saldo no puede ser blanco-vacio-nulo")
	private BigDecimal saldoInicial; 
	
	@ValueOfEnum(enumClass = EEstado.class)
    private String estado;
}