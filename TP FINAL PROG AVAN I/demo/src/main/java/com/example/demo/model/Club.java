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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//para indicar que esta clase va a estar representada por una tabla en un motor de base de datos
@Entity
@Table(name="sys_club")
public class Club {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_id")
	private Long id;
	
	
	@NotBlank (message = "El nombre no puede estar vacio")
	@Column(name = "club_nombre")
	private String nombre;
	
	@NotBlank (message = "El pais no puede estar vacio")
	@Column(name = "club_pais")
	private String pais;
	
	@NotNull (message = "La fundacion no puede ser null")
	@Column(name = "club_fundacion")
	private Integer fundacion;
	
	@NotBlank (message = "La liga no puede estar vacia")
	@Column(name = "club_liga")
	private String liga;
	
	// de uno a muchos, una club puede tener varios jugadores, 
	//el CascadeType.ALL lo que hace es que si doy de alta una persona me inserte el jugador, y si no pongo jugador que no lo ponga
	@OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
	private List<Jugador> jugadores;

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

	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	@Override
	public String toString() {
		return "Club [id=" + id + ", nombre=" + nombre + ", pais=" + pais + ", fundacion=" + fundacion + ", liga="
				+ liga + "]";
	}
}
