package com.app.bnc.model.entities;
 
import java.math.BigDecimal;
import java.time.LocalDate;

import com.app.bnc.constraints.ValueOfEnum;
import com.app.bnc.emun.ETipoMovimiento;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType; 

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
@Entity
@Table(name="movimiento")
public class Movimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "nro_cuenta", nullable = false, updatable = false)
	private Cuenta cuenta;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column (name= "fecha")
	private LocalDate fechaMovimiento;
	
	@ValueOfEnum(enumClass = ETipoMovimiento.class)
	@Column(name="tipo_movimiento", length=25)
	private String tipo_movimiento;
	
	@Column(name="monto")
	private BigDecimal monto;
	
	//@DecimalMin(value = "0", inclusive = true)
	@Column(name="saldo")
	private BigDecimal saldo; 	
	
	@Column(name="descripcion",length=135)
	private String descripcion;
}
