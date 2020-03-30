package com.eventapp.EventApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventapp.EventApp.models.Convidado;
import com.eventapp.EventApp.models.Evento;
import com.eventapp.EventApp.repository.ConvidadoRepository;
import com.eventapp.EventApp.repository.EventRepository;

@Controller
public class EventController {

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private ConvidadoRepository convidadoRepository;

	@RequestMapping(value = "cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "event/formEvent";
	}

	@RequestMapping(value = "cadastrarEvento", method = RequestMethod.POST)
	public String form(Evento evento) {
		eventRepository.save(evento);
		return "redirect:/cadastrarEvento";
	}

	@RequestMapping("eventos")
	public ModelAndView listaEventos() {
		ModelAndView view = new ModelAndView("index");
		Iterable<Evento> eventos = eventRepository.findAll();
		view.addObject("eventos", eventos);
		return view;
	}

	@RequestMapping(value ="view/{codigo}", method = RequestMethod.GET)
	public ModelAndView viewEventos(@PathVariable("codigo") Long codigo) {
		ModelAndView view = new ModelAndView("event/detalhesEventos");
		Evento evento = eventRepository.findByCodigo(codigo);
		view.addObject("evento", evento);
		Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
		view.addObject("convidados", convidados);
		return view;
	}
	
	@RequestMapping(value ="view/{codigo}", method = RequestMethod.POST)
	public String viewEventosPost(@PathVariable("codigo") Long codigo, Convidado convidado) {
		Evento evento = eventRepository.findByCodigo(codigo);
		convidado.setEvento(evento);
		convidadoRepository.save(convidado);
		return "redirect:/view/{codigo}";
	}

}
