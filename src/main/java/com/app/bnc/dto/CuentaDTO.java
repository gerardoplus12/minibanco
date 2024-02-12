package com.app.bnc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.app.bnc.constraints.ValueOfEnum;
import com.app.bnc.emun.EEstado;
import com.app.bnc.emun.ETipoCuenta;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
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
public class CuentaDTO implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank (message = "Nro Cuenta no puede ser blanco-vacio-nulo")
	@Size(min = 10, max = 35)
    private String nroCuenta;
	
    private Long idCliente;
	
	@ValueOfEnum(enumClass = ETipoCuenta.class)
	private String tipo;
	
	@DecimalMin(value = "0", inclusive = true)
	private BigDecimal saldoInicial; 
	
	@ValueOfEnum(enumClass = EEstado.class)
	@Column(name="estado", length=20, nullable=false)
    private String estado;
}
