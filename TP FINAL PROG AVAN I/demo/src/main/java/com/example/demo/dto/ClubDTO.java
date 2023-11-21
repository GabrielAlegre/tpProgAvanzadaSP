package com.example.demo.dto;

import java.util.List;

//DTO: Data Transfer Object para enmascarar el objeto identidad, el que va a ser expuesto en otro objeto donde podemos reducirle
//la cantidad de atributos/objetos o dejarlo exactamente igual
public class ClubDTO {

	private Long id;
	
	
	private String nombre;
	
	
	private String pais;
	

	private Integer fundacion;
	

	private String liga;
	
	
	private List<JugadorDTO> jugadores;


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


	public String getPais() {
		return pais;
	}


	public void setPais(String pais) {
		this.pais = pais;
	}


	public Integer getFundacion() {
		return fundacion;
	}


	public void setFundacion(Integer fundacion) {
		this.fundacion = fundacion;
	}


	public String getLiga() {
		return liga;
	}


	public void setLiga(String liga) {
		this.liga = liga;
	}


	public List<JugadorDTO> getJugadores() {
		return jugadores;
	}


	public void setJugadores(List<JugadorDTO> jugadores) {
		this.jugadores = jugadores;
	}
	
	
}
