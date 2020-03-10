package com.eventapp.EventApp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventapp.EventApp.models.Evento;

public interface EventRepository extends CrudRepository<Evento, Long>{
	Evento findByCodigo(long codigo);
}
