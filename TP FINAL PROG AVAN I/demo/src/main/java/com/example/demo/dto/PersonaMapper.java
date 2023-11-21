package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Domicilio;
import com.example.demo.model.Persona;

//para que construya la clase como un singleton
@Component
public class PersonaMapper {

	@Autowired 
	DomicilioMapper domicilioMapper;
	
	public Persona dtoToEntity(PersonaDTO dto) {
		Persona entity = new Persona();
		entity.setId(dto.getId());
		entity.setNombre(dto.getNombre());
		entity.setApellido(dto.getApellido());
		entity.setSexo(dto.getSexo());
		entity.setDni(dto.getDni());
		entity.setDomicilios(new ArrayList<Domicilio>());
		
		for (DomicilioDTO d : dto.getDomicilios()) {
			entity.getDomicilios().add(domicilioMapper.dtoToEntity(d));
		}
		
		return entity;
	}
	
	public PersonaDTO entityToDto(Persona entity) {
		PersonaDTO dto = new PersonaDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		dto.setApellido(entity.getApellido());
		dto.setSexo(entity.getSexo());
		dto.setDni(entity.getDni());
		dto.setDomicilios(domicilioMapper.lstEntityToLstDto(entity.getDomicilios()));
		
		return dto;
	}
	
	public List<PersonaDTO> lstEntityToLstDto(List<Persona> entity) {
		List<PersonaDTO> dto = new ArrayList<>();
	
		for (Persona enti : entity) {
			dto.add(this.entityToDto(enti));
		}
		return dto;
	}
}
