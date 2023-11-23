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
		/*verifico si quieren modificar los jugadores que esten asociado al club: HAY DOS OPCIONES
		 * 1: Solo quiera modificar los datos del club, nombre, la liga donde juega, etc pero NO los jugadores
		 * 2: Quiera modificar los jugadores pertenecientes al club y algun dato propio del club
		*/
		if(c.getJugadores()!=null)//opcion 2, que tambien quiera modificar los jugadores del club
		{
		    List<Jugador> jugadoresAntiguos = this.jugadorRepository.findJugadoresByClubId(c.getId());
			for (Jugador j : jugadoresAntiguos) {
				this.jugadorRepository.delete(j);
			}
			Club clubModificadoConJugadoresNuevos = this.clubMapper.dtoToEntity(c);
			for (Jugador j : clubModificadoConJugadoresNuevos.getJugadores()) {
				j.setClub(clubModificadoConJugadoresNuevos);
			}
			this.clubRepository.save(clubModificadoConJugadoresNuevos);
		}
		else//opcion 1 solo quiera modificar algun dato del club (nombre, la liga donde juega, fundacion) dejando los mismos jugadores
		{
			System.out.println("entre");
		    c.setJugadores(new ArrayList<JugadorDTO>());
			Club clubModificado = this.clubMapper.dtoToEntity(c);
			this.clubRepository.save(clubModificado);
			
		}

	}

}
