package com.wildcodeschool.WildCircus.controller;

import com.wildcodeschool.WildCircus.entity.Event;
import com.wildcodeschool.WildCircus.entity.Wilder;
import com.wildcodeschool.WildCircus.repository.ClientRepository;
import com.wildcodeschool.WildCircus.repository.EventRepository;
import com.wildcodeschool.WildCircus.repository.WilderRepository;
import com.wildcodeschool.WildCircus.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    WilderRepository wilderRepository;

    private StorageService storageService;

    @Autowired
    public void FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/admin")
    public String homeAdmin(Model model){
        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("wilders", wilderRepository.findAll());
        return"home_admin";
    }


    @GetMapping("/admin/createWilder")
    public String formWilder(Model model){
        model.addAttribute("wilder", new Wilder());
        model.addAttribute("events", eventRepository.findAll());
        return "form_add_wilder";
    }

    @PostMapping("/admin/wilder")
    public String addWilder(Wilder wilder){
        storageService.store(wilder.getPhoto());
        wilder.setPhoto_url("/files/"+ wilder.getPhoto().getOriginalFilename());
        wilderRepository.save(wilder);
        return "redirect:/";
    }

    @GetMapping("/admin/createEvent")
    public String formEvent(Model model){
        model.addAttribute("event", new Event());
        model.addAttribute("wilders", wilderRepository.findAll());
        return "form_add_event";
    }

    @PostMapping("/admin/event")
    public String addEvent(Event event){
        event.setDate(event.getDate().toString());
        eventRepository.save(event);
        return "redirect:/";
    }

    @GetMapping("/admin/delete/event/{id}")
    public String deleteEvent(@PathVariable long id){
        eventRepository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update/event")
    public String formUpdateEvent(@RequestParam long id, Model model){
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()) {
            model.addAttribute("event", event.get());
            model.addAttribute("wilders", wilderRepository.findAll());
            return "form_update_event";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"L'evenement n'existe pas");
    }

    @PostMapping("/admin/update")
    public String updateEvent(@ModelAttribute Event event){
        eventRepository.save(event);
        return "redirect:/admin";
    }









    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
