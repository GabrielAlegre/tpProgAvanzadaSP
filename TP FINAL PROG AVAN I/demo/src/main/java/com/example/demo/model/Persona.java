package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


//para indicar que esta clase va a estar representada por una tabla en un motor de base de datos
@Entity
@Table(name="sys_persona")
public class Persona {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "per_id")
	private Long id;
	
	@NotBlank (message = "El nombre no puede estar vacio")
	@Column(name = "per_nombre")
	private String nombre;
	
	@NotBlank (message = "El apellido no puede estar vacio")
	@Column(name = "per_apellido")
	private String apellido;
	
	@NotNull (message = "El DNI no puede ser null")
	@Column(name = "per_dni")
	private Integer dni;
	
	@Column(name = "per_sexo")
	private String sexo;
	
	// de uno a muchos, una persona puede tener varios domicilios, el CascadeType.ALL lo que hace es que si doy de alta una persona me inserte el domiciolio, y si no pongo domicilio que no lo ponga
	//@OneToMany(mappedBy = "p", cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "p", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Domicilio> domicilios;
	
	public List<Domicilio> getDomicilios() {
		return domicilios;
	}
	public void setDomicilios(List<Domicilio> domicilios) {
		this.domicilios = domicilios;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	//para que no se persista
	@Transient
	private String algo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + "]";
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Integer getDni() {
		return dni;
	}
	public void setDni(Integer dni) {
		this.dni = dni;
	}
	
}
