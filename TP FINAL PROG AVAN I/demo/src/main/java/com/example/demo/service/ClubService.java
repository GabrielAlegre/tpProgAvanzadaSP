package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClubDTO;
import com.example.demo.dto.ClubMapper;
import com.example.demo.dto.JugadorDTO;
import com.example.demo.model.Club;
import com.example.demo.model.Jugador;
import com.example.demo.repository.ClubRepository;
import com.example.demo.repository.JugadorRepository;

@Service
public class ClubService {
	@Autowired
	ClubRepository clubRepository;
	@Autowired
	JugadorRepository jugadorRepository;
	@Autowired
	ClubMapper clubMapper;

	public void modificarClub(ClubDTO c) {
		//busco los jugadores antiguos antes del put para luego eliminarlos y guardar los nuevos
		if(c.getJugadores()!=null)
		{
		    List<Jugador> jugadoresAntiguos = this.jugadorRepository.findJugadoresByClubId(c.getId());
			for (Jugador j : jugadoresAntiguos) {
				System.out.println(j);
				this.jugadorRepository.delete(j);
			}
			Club clubModificado = this.clubMapper.dtoToEntity(c);
			for (Jugador j : clubModificado.getJugadores()) {
				j.setClub(clubModificado);
			}
			this.clubRepository.save(clubModificado);
		}
		else
		{
			System.out.println("entre");
		    c.setJugadores(new ArrayList<JugadorDTO>());
			Club clubModificado = this.clubMapper.dtoToEntity(c);
			this.clubRepository.save(clubModificado);
			
		}

	}

}
