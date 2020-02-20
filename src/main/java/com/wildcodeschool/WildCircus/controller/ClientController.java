package com.wildcodeschool.WildCircus.controller;

import com.wildcodeschool.WildCircus.entity.Client;
import com.wildcodeschool.WildCircus.entity.Event;
import com.wildcodeschool.WildCircus.repository.ClientRepository;
import com.wildcodeschool.WildCircus.repository.EventRepository;
import com.wildcodeschool.WildCircus.repository.WilderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    WilderRepository wilderRepository;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("events", eventRepository.findTop2ByOrderByDateAsc());
        model.addAttribute("wilders", wilderRepository.findAll());
        return "index";
    }

    @RequestMapping("events")
    public String liste_events(Model model){
        model.addAttribute("events", eventRepository.findAll());
        return "liste_events";
    }

    @GetMapping("order")
    public String formBookingTicket(Model model){
        model.addAttribute("client", new Client());
        model.addAttribute("events", eventRepository.findAll());
        return "form_booking";
    }

    @PostMapping("order")
    public String bookingTicket(@ModelAttribute Client client, @ModelAttribute Event event){
        clientRepository.save(client);
        event.setCapacite(event.getCapacite()-1);
        eventRepository.save(event);
        return "redirect:/";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
}
