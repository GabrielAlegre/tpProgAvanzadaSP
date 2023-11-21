package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Club;

//Esta interfaz la utilizamos para hacer todo lo que es persistencia, funciona por reflexion, busca la anotacion @colum @id y arma la query y la ejecuta
//lo hace singleton, si ya existe un objeto de esta clase guarda la instancia y listo sino lo crea por unica vez, para no llenar el servidor de objetos
@Repository
public interface ClubRepository extends CrudRepository<Club, Long>{

	List<Club> findAllByPais(String pais);
	
	@Query("select c from Club c "
			+ "inner join c.jugadores j "
			+"where c.liga =:ligue "
			+ "and j.posicion = :posicion")
	List<Club> buscarClubesDeUnaLigaPorPosicion(String ligue, String posicion);
	
	@Query(nativeQuery = true, value = "SELECT * FROM sys_club c WHERE c.club_nombre LIKE '%FC%' ORDER BY c.club_fundacion DESC")
	List<Club> buscarClubesQueEnSuNombreContenganFC();
}
