package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Jugador;

@Repository
public interface JugadorRepository extends CrudRepository<Jugador, Long>{

}
