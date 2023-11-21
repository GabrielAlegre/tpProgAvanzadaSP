package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Club;


//para que construya la clase como un singleton
@Component
public class ClubMapper {

	@Autowired
	JugadorMapper jugadorMapper;
	
	public Club dtoToEntity(ClubDTO dto) {
		Club entity = new Club();
		entity.setId(dto.getId());
		entity.setNombre(dto.getNombre());
		entity.setFundacion(dto.getFundacion());
		entity.setLiga(dto.getLiga());
		entity.setPais(dto.getPais());
		entity.setJugadores(jugadorMapper.lstDtoToLstEntity(dto.getJugadores()));
		
		return entity;
	}
	
	public ClubDTO entityToDto(Club entity) {
		ClubDTO dto = new ClubDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		dto.setFundacion(entity.getFundacion());
		dto.setLiga(entity.getLiga());
		dto.setPais(entity.getPais());
		dto.setJugadores(jugadorMapper.lstEntityToLstDto(entity.getJugadores()));
		
		return dto;
	}
	
	public List<ClubDTO> lstEntityToLstDto(List<Club> entity) {
		List<ClubDTO> dto = new ArrayList<>();
	
		for (Club enti : entity) {
			dto.add(this.entityToDto(enti));
		}
		return dto;
	}
}
