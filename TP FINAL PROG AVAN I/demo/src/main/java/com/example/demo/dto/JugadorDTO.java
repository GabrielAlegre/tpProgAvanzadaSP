package com.example.demo.dto;


public class JugadorDTO {
	
	private Long id;
	
	
	private String nombre;
	
	
	private String apellido;
	

	private String posicion;
	
	private String equipoDondeJuega;


	public String getEquipoDondeJuega() {
		return equipoDondeJuega;
	}


	public void setEquipoDondeJuega(String equipoDondeJuega) {
		this.equipoDondeJuega = equipoDondeJuega;
	}


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


	

	@Override
	public String toString() {
		return "JugadorDTO [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", posicion=" + posicion
				+ "]";
	}	

}
