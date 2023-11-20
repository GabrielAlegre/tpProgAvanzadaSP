package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Domicilio;

@Repository
public interface DomicilioRepository extends CrudRepository<Domicilio, Long> {
	@Query("SELECT d FROM Domicilio d WHERE d.p.id = :personaId")
	List<Domicilio> findDomiciliosByPersonaId(@Param("personaId") Long personaId);
}
