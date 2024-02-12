package com.app.bnc.model.entities;
 
import com.app.bnc.emun.EGenero;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.ToString; 

@ToString
@Entity
public class Persona implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@NotBlank (message = "Id de Identificacion no debe ser blanco-vacio-nulo")
	@Size(max = 20)
    @Column(name="id_persona", length=20)
    private String id;

	@NotBlank (message = "Nombre no puede ser blanco-vacio-nulo")
	@Size(max = 50)
	@Column(name="nombre", length=30)
    private String nombre;
	
	@Enumerated(EnumType.STRING)
	@Column(name="genero",length=30)
	private EGenero genero; 
	
	@Column(name="edad")
    private Integer edad; 
    
    @NotBlank (message = "Dirección no puede ser blanco-vacio-nulo")
   	@Size(max = 150)
    @Column(name="direccion", length=150)
    private String direccion;
    
   	@Size(max = 20)
    @Column(name="telefono", length=20)
    private String telefono;
   
    //GETTER Y SETTER
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}

	public EGenero getGenero() {
		return genero;
	}

	public void setGenero(EGenero genero) {
		this.genero = genero;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	//CONSTRUCTORES
    public Persona() {
    }
    
    public Persona(String id, String nombre, EGenero genero, int edad, String direccion, String telefono) {
    	this.id = id;
    	this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    
    public Persona(String nombre, EGenero genero, int edad, String direccion, String telefono) {
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}