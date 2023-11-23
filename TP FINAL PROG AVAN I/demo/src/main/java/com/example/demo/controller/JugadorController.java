package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ClubMapper;
import com.example.demo.dto.JugadorDTO;
import com.example.demo.dto.JugadorMapper;
import com.example.demo.model.Club;
import com.example.demo.model.Jugador;
import com.example.demo.repository.ClubRepository;
import com.example.demo.repository.JugadorRepository;
import com.example.demo.service.ClubService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class JugadorController {
	@Autowired
	ClubMapper clubMapper;
	@Autowired
	JugadorMapper jugadorMapper;
	@Autowired
	ClubRepository clubRepository;
	@Autowired
	JugadorRepository jugadorRepository;
	@Autowired
	ClubService clubService;
	
	@GetMapping("/jugador/{id}")
	//@PathVariable es para configurar que el parametro lo va a sacar de la ruta
	public ResponseEntity<?> obtenerJugador(@PathVariable Long id) {
		Optional<Jugador> pOpt= jugadorRepository.findById(id);
		if(pOpt.isPresent())
		{
			return new ResponseEntity<JugadorDTO>(jugadorMapper.entityToDto(pOpt.get()), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Jugador no encontrado con el id: "+id, HttpStatus.CONFLICT);
		}			
	}
	
	@GetMapping("/jugadores")
	public ResponseEntity<?> obtenerJugadores() {
		List<Jugador> prs = (List<Jugador>) jugadorRepository.findAll();
		
		return new ResponseEntity<List<JugadorDTO>>(jugadorMapper.lstEntityToLstDto(prs), HttpStatus.OK);
	}
	
	@PostMapping("/jugador")
	public ResponseEntity<?> crearJugador(@RequestBody @Validated Jugador j) {
		/*Si el club es null (no me pasa ningun id del club cuando hace el post) tomo como que
		quiere dar de alta un jugador libre que no este asociado a ningun club que no tendria contrato con ningun club*/
		if(j.getClub()==null)
		{
			jugadorRepository.save(j);
			return new ResponseEntity<String>("Se creo el jugador "+ j.getNombre(), HttpStatus.OK);
		}
		else//si el club no es nulo es xq me paso algun id de un club en post y busco si existe un club con ese id
		{
			Optional<Club> cOpt = clubRepository.findById(j.getClub().getId());
			if(cOpt.isPresent())
			{
				jugadorRepository.save(j);
				return new ResponseEntity<String>("Se creo el jugador "+ j.getNombre()+ " Perteneciente al club: "+ cOpt.get().getNombre(), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No se pudo realizar el alta del jugador xq NO existe un club con el id: "+j.getClub().getId(), HttpStatus.CONFLICT);
			}	
		}
	}
	
	@PutMapping("/jugador")
	public ResponseEntity<?> modificarClub(@RequestBody @Validated Jugador j) {
		if(j.getId()==null)
		{
			return new ResponseEntity<String>("Para modificar un jugador debe pasar un id", HttpStatus.CONFLICT);
		}
		else {
			Optional<Jugador> cOpt = this.jugadorRepository.findById(j.getId());
			if(cOpt.isPresent()){
				if(j.getClub()!=null)
				{
					Optional<Club> clbuOpt = clubRepository.findById(j.getClub().getId());
					if(clbuOpt.isPresent())
					{
						jugadorRepository.save(j);
						return new ResponseEntity<String>("Se modifico el jugador", HttpStatus.OK);
					}
					else {
						return new ResponseEntity<String>("No se pudo realizar la modificacion xq NO existe un club con el id: "+j.getClub().getId(), HttpStatus.CONFLICT);
					}	
				}
				else
				{
					jugadorRepository.save(j);
					return new ResponseEntity<String>("Se modifico el jugador, ahora esta libre, no tiene club", HttpStatus.CONFLICT);
				}
			}
			else {
				return new ResponseEntity<String>("No se encontro a ningun jugador con el id: "+j.getId(), HttpStatus.CONFLICT);
			}
		}
	}
	
	@DeleteMapping("/jugador")
	public ResponseEntity<?> eliminarJugador(@RequestBody @Valid Jugador j) {
		if(j.getId()==null)
		{
			return new ResponseEntity<String>("Para eliminar un jugador debe pasar un id", HttpStatus.CONFLICT);
		}
		else
		{
			Optional<Jugador> cOpt = this.jugadorRepository.findById(j.getId());
			if(cOpt.isPresent()){
				jugadorRepository.delete(j);
				return new ResponseEntity<String>("Se elimino el jugador "+j.getNombre(), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No se encontro a ningun Jugador con el id: "+j.getId(), HttpStatus.CONFLICT);
			}
		}
	}
	
}
