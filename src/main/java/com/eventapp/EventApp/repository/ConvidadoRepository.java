package com.eventapp.EventApp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventapp.EventApp.models.Convidado;
import com.eventapp.EventApp.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
	Iterable<Convidado> findByEvento(Evento evento);

	Convidado findByRg(String rg);

}
