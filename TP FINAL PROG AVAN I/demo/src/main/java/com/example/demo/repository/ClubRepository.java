package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Club;

//Esta interfaz la utilizamos para hacer todo lo que es persistencia, funciona por reflexion, busca la anotacion @colum @id y arma la query y la ejecuta
//lo hace singleton, si ya existe un objeto de esta clase guarda la instancia y listo sino lo crea por unica vez, para no llenar el servidor de objetos
@Repository
public interface ClubRepository extends CrudRepository<Club, Long>{

}
