package com.example.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Persona;



//Esta interfaz la utilizamos para hacer todo lo que es persistencia, funciona por reflexion, busca la anotacion @colum @id y arma la query y la ejecuta
//lo hace singleton, si ya existe un objeto de esta clase guarda la instancia y listo sino lo crea por unica vez, para no llenar el servidor de objetos
@Repository
public interface PersonaRepository extends CrudRepository<Persona, Long> {
	
	List<Persona> findAllByApellido(String apellido);
	List<Persona> findAllByApellidoAndNombre(String apellido, String nombre);
	
	@Query("select p from Persona p "
			+ "inner join p.domicilios d "
			+"where p.apellido =:lastName "
			+ "and d.calle = :calle")
	List<Persona> buscarPorDatos(String lastName, String calle);
}
