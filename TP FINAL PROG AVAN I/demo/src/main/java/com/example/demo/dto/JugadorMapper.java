package com.example.demo.dto;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.Jugador;

@Component
public class JugadorMapper {

	public Jugador dtoToEntity(JugadorDTO dto) {
		Jugador entity = new Jugador();
		entity.setNombre(dto.getNombre());
		entity.setApellido(dto.getApellido());
		entity.setPosicion(dto.getPosicion());
		entity.setId(dto.getId());
		return entity;
	}
	
	
	public JugadorDTO entityToDto(Jugador entity) {
		JugadorDTO dto = new JugadorDTO();
		dto.setNombre(entity.getNombre());
		dto.setApellido(entity.getApellido());
		dto.setPosicion(entity.getPosicion());
		dto.setId(entity.getId());
		//nueva
		dto.setEquipoDondeJuega(entity.getClub().getNombre());
		return dto;
	}
	
	public List<JugadorDTO> lstEntityToLstDto(List<Jugador> entity) {
		List<JugadorDTO> dto = new ArrayList<>();
	
		for (Jugador enti : entity) {
			dto.add(this.entityToDto(enti));
		}
		return dto;
	}
	
	public List<Jugador> lstDtoToLstEntity(List<JugadorDTO> entity) {
		List<Jugador> dto = new ArrayList<>();
	
		for (JugadorDTO enti : entity) {
			dto.add(this.dtoToEntity(enti));
		}
		return dto;
	}
	
}
