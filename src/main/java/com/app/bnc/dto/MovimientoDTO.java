package com.app.bnc.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.app.bnc.constraints.ValueOfEnum;
import com.app.bnc.emun.ETipoMovimiento;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class MovimientoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Long id;
	
    @NotBlank (message = "Nro cuenta no puede ser blanco-vacio-nulo")
	private String nroCuenta;
	
	private Long idCliente;

	@NotBlank (message = "Fecha no puede ser blanco-vacio-nulo")
	@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column (name= "fecha")
	private LocalDate fechaMovimiento; 
	
	@ValueOfEnum(enumClass = ETipoMovimiento.class)
	private ETipoMovimiento tipo_movimiento;
	
	@NotBlank (message = "Monto no puede ser blanco-vacio-nulo")
	private BigDecimal monto;
	
	@Column(name="descripcion")
	private String descripcion;
}
