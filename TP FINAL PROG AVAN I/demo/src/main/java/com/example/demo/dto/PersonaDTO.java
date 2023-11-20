package com.example.demo.dto;

import java.util.List;

//DTO: Data Transfer Object para enmascarar el objeto identidad, el que va a ser expuesto en otro objeto donde podemos reducirle
//la cantidad de atributos/objetos o dejarlo exactamente igual
public class PersonaDTO {
	private Long id;
	private String nombre;
	private String apellido;
	private Integer dni;
	private String sexo;
	private List<DomicilioDTO> domicilios;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public List<DomicilioDTO> getDomicilios() {
		return domicilios;
	}
	public void setDomicilios(List<DomicilioDTO> domicilios) {
		this.domicilios = domicilios;
	}
	
	
}
