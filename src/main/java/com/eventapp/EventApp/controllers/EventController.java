package com.eventapp.EventApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eventapp.EventApp.models.Evento;
import com.eventapp.EventApp.repository.EventRepository;

@Controller
public class EventController {
	
	@Autowired
	private EventRepository eventRepository;
	
	@RequestMapping(value ="cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return"event/formEvent";
	}
	@RequestMapping(value ="cadastrarEvento", method = RequestMethod.POST)
	public String form(Evento evento) {
		eventRepository.save(evento);
		return"redirect:/cadastrarEvento";
	}

}
