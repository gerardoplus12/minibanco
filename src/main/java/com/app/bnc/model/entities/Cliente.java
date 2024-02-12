package com.app.bnc.model.entities;
 
import java.io.Serializable;
import java.util.List;

import com.app.bnc.constraints.ValueOfEnum;
import com.app.bnc.emun.EEstado;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cliente")
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente") 
    private Long idCliente;

	@NotBlank (message = "Password no puede ser vacio")
	@Size(max = 60)
	@Column(name="contrasena", length=60, nullable=false)
    private String contrasena;
	
	@ValueOfEnum(enumClass = EEstado.class)
	@Column(name="estado", length=20, nullable=false)
    private String estado;
	
	//LINKS
    @OneToOne
    @JoinColumn(name = "id_persona", updatable = false, nullable = false)
    private Persona persona;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Cuenta> listCuentas;
    
    //Constructores Custom
	public Cliente(Long idCliente, Persona persona, String contrasena, String estado) {
		this.idCliente = idCliente;
		this.persona = persona;
		this.contrasena = contrasena;
		this.estado = estado; 
	}
	
	public Cliente(Persona persona, String contrasena, String estado) {
		this.persona = persona;
		this.contrasena = contrasena;
		this.estado = estado;
	}
		
	@Override
	public String toString() { 
	    StringBuilder sb = new StringBuilder(); 
	    sb.append("idCliente:");
	    sb.append(this.idCliente);
	    sb.append("|identificacion:");
	    sb.append(this.persona.getId()); //Identificacion
	    sb.append("|nombre:");
	    sb.append(this.persona.getNombre());
	    sb.append("|genero:");
	    sb.append(this.persona.getGenero());
	    sb.append("|edad:");
	    sb.append(this.persona.getEdad());
	    sb.append("|direccion:");
	    sb.append(this.persona.getDireccion());
	    sb.append("|telefono:");
	    sb.append(this.persona.getTelefono());
	    sb.append("|contraseña:");
	    sb.append(this.getContrasena());
	    sb.append("|estado:");
	    sb.append(this.getEstado());
	    return sb.toString(); 
	} 
}
