package com.example.demo.dto;




import org.springframework.stereotype.Component;

import com.example.demo.model.Domicilio;



@Component
public class DomicilioMapper {

	
	public Domicilio dtoToEntity(DomicilioDTO dto) {
		Domicilio entity = new Domicilio();
		entity.setCalle(dto.getCalle());
		entity.setAltura(dto.getAltura());
		entity.setId(dto.getId());
		return entity;
	}
	
	public DomicilioDTO entityToDto(Domicilio entity) {
		DomicilioDTO dto = new DomicilioDTO();
		dto.setCalle(entity.getCalle());
		dto.setAltura(entity.getAltura());
		dto.setId(entity.getId());
		return dto;
	}
}
