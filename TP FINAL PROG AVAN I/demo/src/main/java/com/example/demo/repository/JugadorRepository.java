package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Jugador;

@Repository
public interface JugadorRepository extends CrudRepository<Jugador, Long>{
	@Query("SELECT j FROM Jugador j WHERE j.club.id = :clubId")
	List<Jugador> findJugadoresByClubId(@Param("clubId") Long clubId);
}
