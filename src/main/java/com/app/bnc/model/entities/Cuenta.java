package com.app.bnc.model.entities;
 
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.app.bnc.constraints.ValueOfEnum;
import com.app.bnc.emun.EEstado;
import com.app.bnc.emun.ETipoCuenta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name="cuenta")
public class Cuenta implements Serializable {

	private static final long serialVersionUID = 1L; 

	@Id
	@Size(min = 10, max = 35)
	@NotBlank (message = "Nro de cuenta no puede ser vacio")
	@Column(name = "nro_cuenta", length=35) 
    private String nroCuenta;
	
	@ValueOfEnum(enumClass = ETipoCuenta.class)
	@Column(name="tipo_cuenta", length=25)
	private String tipo;
	
	@DecimalMin(value = "0", inclusive = true)
	@Column(name="saldo_inicial")
	private BigDecimal saldoInicial; 
	
	@ValueOfEnum(enumClass = EEstado.class)
	@Column(name="estado", length=20, nullable=false)
    private String estado;
	
	//LINKS 
	@ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false, updatable = false)
    private Cliente cliente;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
	private List<Movimiento> listMovimientos;
	
}
