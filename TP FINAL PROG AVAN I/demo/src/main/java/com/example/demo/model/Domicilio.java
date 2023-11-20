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
@Table(name="sys_domicilio")
public class Domicilio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dom_id")
	private Long id;
	
	@Column(name = "dom_calle")
	private String calle;
	
	@Column(name = "dom_altura")
	private Integer altura;
	
	@ManyToOne
	@JoinColumn(name="per_id")
	private Persona p;
	
	public Persona getPersona() {
		return p;
	}
	public void setPersona(Persona persona) {
		this.p = persona;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public Integer getAltura() {
		return altura;
	}
	public void setAltura(Integer altura) {
		this.altura = altura;
	}
	@Override
	public String toString() {
		return "Domicilio [id=" + id + ", calle=" + calle + ", altura=" + altura + ", p=" + p + "]";
	}
	

}
