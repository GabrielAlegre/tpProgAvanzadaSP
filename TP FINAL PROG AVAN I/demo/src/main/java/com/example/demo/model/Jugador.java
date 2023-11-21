package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="sys_jugador")
public class Jugador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "jug_id")
	private Long id;
	
	@Column(name = "jug_nombre")
	private String nombre;
	
	@Column(name = "jug_apellido")
	private String apellido;
	
	@Column(name = "jug_posicion")
	private String posicion;
	
	@ManyToOne
	@JoinColumn(name="club_id")
	private Club club;

	
	
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



	public String getPosicion() {
		return posicion;
	}



	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}



	public Club getClub() {
		return club;
	}



	public void setClub(Club club) {
		this.club = club;
	}



	@Override
	public String toString() {
		return "Jugador [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", posicion=" + posicion
				+ ", club=" + club + "]";
	}
	
	
}
