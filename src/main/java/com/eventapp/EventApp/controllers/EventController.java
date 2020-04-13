package com.eventapp.EventApp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String form(Evento evento, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarEvento";
		}
		eventRepository.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
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
	public String viewEventosPost(@PathVariable("codigo") Long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "verifique os campos!");
			return "redirect:/view/{codigo}";
		}
		Evento evento = eventRepository.findByCodigo(codigo);
		convidado.setEvento(evento);
		convidadoRepository.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
		return "redirect:/view/{codigo}";
	}
	
	@RequestMapping("deleteEvento")
	public String deleteEvento(Long codigo) {
		Evento evento = eventRepository.findByCodigo(codigo);
		eventRepository.delete(evento);
		return "redirect:/eventos";
	}
	
	@RequestMapping("deleteConvidado")
	public String deleteConvidado(String rg) {
		Convidado convidado = convidadoRepository.findByRg(rg);
		convidadoRepository.delete(convidado);
		Evento evento = convidado.getEvento();
		String codigo = String.valueOf(evento.getCodigo());
		return "redirect:/"+codigo;
	}
}
