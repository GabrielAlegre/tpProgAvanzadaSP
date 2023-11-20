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

import com.example.demo.dto.PersonaDTO;
import com.example.demo.dto.PersonaMapper;
import com.example.demo.model.Domicilio;
import com.example.demo.model.Persona;
import com.example.demo.repository.DomicilioRepository;
import com.example.demo.repository.PersonaRepository;

import jakarta.validation.Valid;

//DTO: se utiliza para pasar del backend al frontnend y del frontend al backend, para enmascarar el objeto identidad en otro objeto donde podemos reducirle
//la cantidad de objetos o dejarlo exactamente igual

//Para que todos los métodos que estén dentro de una clase que este tageada con @RestController  los podamos identificar 
//con otras anotaciones, es decir, setteamos un endpoint
@RestController
//para indicar que todos los endpoin que esten en la clase esten en /api
@RequestMapping("/api")
public class PersonaController {
	
	//lo que hace es que generar un objeto para que nosotros no hagamos ningun new, arma singleton para no armar multiples objetos
	@Autowired
	PersonaRepository personaRepository;
	@Autowired
	DomicilioRepository domicilioRepository;
	@Autowired
	PersonaMapper personaMapper;
	
	@GetMapping("/saludar")
	public String saludo() {
		return "tiene que aparecer 'hola' en localhost:8080/saludar";
	}
	
	@GetMapping("/persona/{id}")
	//@PathVariable es para configurar que el parametro lo va a sacar de la ruta
	public ResponseEntity<?> obtenerPersona(@PathVariable Long id) {
		Optional<Persona> pOpt= personaRepository.findById(id);
		if(pOpt.isPresent())
		{
			return new ResponseEntity<PersonaDTO>(personaMapper.entityToDto(pOpt.get()), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Persona no encontrada con el id: "+id, HttpStatus.CONFLICT);
		}			
	}
	
	@GetMapping("/personas")
	public ResponseEntity<?> obtenerPersonas() {
		List<Persona> prs = (List<Persona>) personaRepository.findAll();
		
		return new ResponseEntity<List<PersonaDTO>>(personaMapper.lstEntityToLstDto(prs), HttpStatus.OK);
	}
	
	//Lo ejecuto con el postman
	@PostMapping("/persona")
	public ResponseEntity<?> crearPersona(@RequestBody @Valid Persona p) {
		System.out.println(p.toString());
		for (Domicilio d : p.getDomicilios()) {
			d.setPersona(p);
		}
		personaRepository.save(p);
		return new ResponseEntity<String>("Se creoo", HttpStatus.OK);
	}
	
	//Lo ejecuto con el postman
	@DeleteMapping("/persona")
	public ResponseEntity<?> eliminarPersona(@RequestBody @Valid Persona p) {
		System.out.println(p.toString());
		personaRepository.delete(p);
		return new ResponseEntity<String>("Se elimino", HttpStatus.OK);
	}
	
	//Lo ejecuto con el postman
	/*@PutMapping("/persona")
	public ResponseEntity<?> modificarPersona(@RequestBody @Valid PersonaDTO p) {
		//Para hacer un update previamente hay que verificar que realmente quiera modificar ya que usa el save
		//xq si nos ejecutan el metodo modificar pero no ponen un id el servidor va hacer un insert
		if(p.getId()==null)
		{
			return new ResponseEntity<String>("Para modificar una persona debe pasar un id", HttpStatus.CONFLICT);
		}
		else {
			Optional<Persona> pOpt = this.personaRepository.findById(p.getId());
			if(pOpt.isPresent()){
				this.personaRepository.save(this.personaMapper.dtoToEntity(p));
				return new ResponseEntity<String>("Se modifico", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No se encontro a la persona con id: "+p.getId(), HttpStatus.CONFLICT);
			}
		}
	}*/
	
	@PutMapping("/persona")
	public ResponseEntity<?> modificarPersona(@RequestBody @Valid PersonaDTO p) {
		//Para hacer un update previamente hay que verificar que realmente quiera modificar ya que usa el save
		//xq si nos ejecutan el metodo modificar pero no ponen un id el servidor va hacer un insert
		if(p.getId()==null)
		{
			return new ResponseEntity<String>("Para modificar una persona debe pasar un id", HttpStatus.CONFLICT);
		}
		else {
			Optional<Persona> pOpt = this.personaRepository.findById(p.getId());
			if(pOpt.isPresent()){
				
				//busco los domicilios antiguos antes del put para luego eliminarlos y guardar los nuevos
			    List<Domicilio> domiciliosAntiguos = this.domicilioRepository.findDomiciliosByPersonaId(p.getId());
				for (Domicilio d : domiciliosAntiguos) {
					System.out.println(d);
					this.domicilioRepository.delete(d);
				}
				Persona perModificada = this.personaMapper.dtoToEntity(p);
				for (Domicilio d : perModificada.getDomicilios()) {
					d.setPersona(perModificada);
					System.out.println(d);
				}
				this.personaRepository.save(perModificada);
				return new ResponseEntity<String>("Se modifico", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No se encontro a la persona con id: "+p.getId(), HttpStatus.CONFLICT);
			}
		}
	}
	
	//-----------------------------------------CONSULTAS PERSONALIZADAS------------------------------------------
	
	@GetMapping("/personaByApellido/{lastName}")
	//@PathVariable es para configurar que el parametro lo va a sacar de la ruta
	public ResponseEntity<?> obtenerPersona(@PathVariable String lastName) {
		List<Persona> entitys= personaRepository.findAllByApellido(lastName);
		 return !entitys.isEmpty()? new ResponseEntity<List<PersonaDTO>>(personaMapper.lstEntityToLstDto(entitys), HttpStatus.OK)
				 : new ResponseEntity<String>("No se encontro ninguna persona con el apellido: "+lastName, HttpStatus.CONFLICT);
		
	}
	
	@GetMapping("/personaByApellidoAndNombre/{lastName}/{nombre}")
	//@PathVariable es para configurar que el parametro lo va a sacar de la ruta
	public ResponseEntity<?> obtenerPersonaPorApellidoNombre(@PathVariable String lastName, @PathVariable String nombre) {
		List<Persona> entitys= personaRepository.findAllByApellidoAndNombre(lastName, nombre);
		 return !entitys.isEmpty()? new ResponseEntity<List<PersonaDTO>>(personaMapper.lstEntityToLstDto(entitys), HttpStatus.OK)
				 : new ResponseEntity<String>("No se encontro ninguna persona con nombre y apellido: "+nombre+" "+lastName, HttpStatus.CONFLICT);
		
	}
	
	@GetMapping("/personaByApellidoAndCalle/{lastName}/{calle}")
	//@PathVariable es para configurar que el parametro lo va a sacar de la ruta
	public ResponseEntity<?> obtenerPersonaPorApellidoCalle(@PathVariable String lastName, @PathVariable String calle) {
		List<Persona> entitys= personaRepository.buscarPorDatos(lastName, calle);
		 return !entitys.isEmpty()? new ResponseEntity<List<PersonaDTO>>(personaMapper.lstEntityToLstDto(entitys), HttpStatus.OK)
				 : new ResponseEntity<String>("No se encontro ninguna persona con apellido y calle: "+lastName+" "+calle, HttpStatus.CONFLICT);
		
	}
}
