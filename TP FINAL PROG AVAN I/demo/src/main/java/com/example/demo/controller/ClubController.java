package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ClubDTO;
import com.example.demo.dto.ClubMapper;
import com.example.demo.model.Club;
import com.example.demo.model.Jugador;
import com.example.demo.repository.ClubRepository;
import com.example.demo.repository.JugadorRepository;
import com.example.demo.service.ClubService;

import jakarta.validation.Valid;

//DTO: se utiliza para pasar del backend al frontnend y del frontend al backend, para enmascarar el objeto identidad en otro objeto donde podemos reducirle
//la cantidad de objetos o dejarlo exactamente igual

//Para que todos los métodos que estén dentro de una clase que este tageada con @RestController  los podamos identificar 
//con otras anotaciones, es decir, setteamos un endpoint
@RestController
//para indicar que todos los endpoin que esten en la clase esten en /api
@RequestMapping("/api")
public class ClubController {
	
	//lo que hace es que generar un objeto para que nosotros no hagamos ningun new, arma singleton para no armar multiples objetos
	@Autowired
	ClubMapper clubMapper;
	@Autowired
	ClubRepository clubRepository;
	@Autowired
	JugadorRepository jugadorRepository;
	@Autowired
	ClubService clubService;
	
	//----------------------------------------- GETS ------------------------------------------
	
	@GetMapping("/club/{id}")
	//@PathVariable es para configurar que el parametro lo va a sacar de la ruta
	public ResponseEntity<?> obteneClub(@PathVariable Long id) {
		Optional<Club> pOpt= clubRepository.findById(id);
		if(pOpt.isPresent())
		{
			return new ResponseEntity<ClubDTO>(clubMapper.entityToDto(pOpt.get()), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Club no encontrado con el id: "+id, HttpStatus.CONFLICT);
		}			
	}
	
	@GetMapping("/clubes")
	public ResponseEntity<?> obtenerClubes() {
		List<Club> prs = (List<Club>) clubRepository.findAll();
		
		return new ResponseEntity<List<ClubDTO>>(clubMapper.lstEntityToLstDto(prs), HttpStatus.OK);
	}
	
	//----------------------------------------- POST ------------------------------------------

	//Lo ejecuto con el postman
	@PostMapping("/club")
	public ResponseEntity<?> crearClub(@RequestBody @Valid Club c) {
		if(c.getJugadores()!=null)
		{
			for (Jugador j : c.getJugadores()) {
				j.setClub(c);
			}
		}
		clubRepository.save(c);
		return new ResponseEntity<String>("Se creo el club "+ c.getNombre(), HttpStatus.OK);
	}
	
	//----------------------------------------- PUT ------------------------------------------
	@PutMapping("/club")
	public ResponseEntity<?> modificarClub(@RequestBody @Valid ClubDTO c) {
		//Previamente verifico que realmente quiera modificar, ya que usa el save
		//si nos ejecutan el metodo modificar pero no ponen un id el servidor va hacer un insert
		if(c.getId()==null)
		{
			return new ResponseEntity<String>("Para modificar un club debe pasar un id", HttpStatus.CONFLICT);
		}
		else {
			Optional<Club> cOpt = this.clubRepository.findById(c.getId());
			if(cOpt.isPresent()){
				clubService.modificarClub(c);
				return new ResponseEntity<String>("Se modifico el club", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No se encontro a ningun club con el id: "+c.getId(), HttpStatus.CONFLICT);
			}
		}
	}
	/*
	@PutMapping("/club")
	public ResponseEntity<?> modificarClub(@RequestBody @Valid ClubDTO c) {
		//Para hacer un update previamente verifico que realmente quiera modificar ya que usa el save
		//xq si nos ejecutan el metodo modificar pero no ponen un id el servidor va hacer un insert
		if(c.getId()==null)
		{
			return new ResponseEntity<String>("Para modificar un club debe pasar un id", HttpStatus.CONFLICT);
		}
		else {
			Optional<Club> cOpt = this.clubRepository.findById(c.getId());
			if(cOpt.isPresent()){
				
				//busco los jugadores antiguos antes del put para luego eliminarlos y guardar los nuevos
			    List<Jugador> jugadoresAntiguos = this.jugadorRepository.findJugadoresByClubId(c.getId());
				for (Jugador j : jugadoresAntiguos) {
					System.out.println(j);
					this.jugadorRepository.delete(j);
				}
				Club clubModificado = this.clubMapper.dtoToEntity(c);
				for (Jugador j : clubModificado.getJugadores()) {
					j.setClub(clubModificado);
					System.out.println(j);
				}
				this.clubRepository.save(clubModificado);
				return new ResponseEntity<String>("Se modifico el club", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No se encontro a ningun club con el id: "+c.getId(), HttpStatus.CONFLICT);
			}
		}
	}*/
	
	//----------------------------------------- DELETE ------------------------------------------
	//Lo ejecuto con el postman
	@DeleteMapping("/club")
	public ResponseEntity<?> eliminarClub(@RequestBody @Valid Club c) {
		if(c.getId()==null)
		{
			return new ResponseEntity<String>("Para eliminar un club debe pasar un id", HttpStatus.CONFLICT);
		}
		else
		{
			Optional<Club> cOpt = this.clubRepository.findById(c.getId());
			if(cOpt.isPresent()){
				clubRepository.delete(c);
				return new ResponseEntity<String>("Se elimino el club "+c.getNombre(), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No se encontro a ningun club con el id: "+c.getId(), HttpStatus.CONFLICT);
			}
		}
	}
	
	//-----------------------------------------QUERYS PERSONALIZADAS------------------------------------------
	@GetMapping("/clubesPorPais/{pais}")
	//@PathVariable es para configurar que el parametro lo va a sacar de la ruta
	public ResponseEntity<?> obtenerClubes(@PathVariable String pais) {
		List<Club> entitys= clubRepository.findAllByPais(pais);
		 return !entitys.isEmpty()? new ResponseEntity<List<ClubDTO>>(clubMapper.lstEntityToLstDto(entitys), HttpStatus.OK)
				 : new ResponseEntity<String>("No se encontro ninguna club del pais: "+pais, HttpStatus.CONFLICT);
		
	}
	@GetMapping("/clubes/{ligue}/{posicion}")
	//@PathVariable es para configurar que el parametro lo va a sacar de la ruta
	public ResponseEntity<?> obtenerClubesDeUnaLigaPorPosicion(@PathVariable String ligue, @PathVariable String posicion) {
		List<Club> entitys= clubRepository.buscarClubesDeUnaLigaPorPosicion(ligue, posicion);
		 return !entitys.isEmpty()? new ResponseEntity<List<ClubDTO>>(clubMapper.lstEntityToLstDto(entitys), HttpStatus.OK)
				 : new ResponseEntity<String>("No se encontro ningun club de la liga '"+ligue+"' que tenga "+posicion, HttpStatus.CONFLICT);
		
	}
	@GetMapping("/clubesConFc")
	public ResponseEntity<?> obtenerClubesQueEnSuNombreContenganFC() {
		List<Club> entitys= clubRepository.buscarClubesQueEnSuNombreContenganFC();
		 return !entitys.isEmpty()? new ResponseEntity<List<ClubDTO>>(clubMapper.lstEntityToLstDto(entitys), HttpStatus.OK)
				 : new ResponseEntity<String>("No se encontro ningun club que tenga 'FC' en su nombre", HttpStatus.CONFLICT);
		
	}

}
