package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ClubDTO;
import com.example.demo.dto.ClubMapper;
import com.example.demo.model.Club;
import com.example.demo.model.Jugador;
import com.example.demo.repository.ClubRepository;
import com.example.demo.repository.JugadorRepository;

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
	
	//Lo ejecuto con el postman
	@PostMapping("/club")
	public ResponseEntity<?> crearClub(@RequestBody @Valid Club c) {
		System.out.println(c.toString());
		for (Jugador j : c.getJugadores()) {
			j.setClub(c);
		}
		clubRepository.save(c);
		return new ResponseEntity<String>("Se creo el club "+ c.getNombre(), HttpStatus.OK);
	}
}
