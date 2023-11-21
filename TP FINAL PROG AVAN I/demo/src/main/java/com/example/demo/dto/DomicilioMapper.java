package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<DomicilioDTO> lstEntityToLstDto(List<Domicilio> entity) {
		List<DomicilioDTO> dto = new ArrayList<>();
	
		for (Domicilio enti : entity) {
			dto.add(this.entityToDto(enti));
		}
		return dto;
	}
}
