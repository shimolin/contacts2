package com.example.contacts2.controller;

import com.example.contacts2.model.Contact;
import com.example.contacts2.service.ContactServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class ContactsController {

    private final ContactServiceImpl contactService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        return "index";
    }

    @GetMapping("/contact/create")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "create";
    }

    @PostMapping("/contact/create")
    public String createSubmit(@ModelAttribute Contact contact) {
        if (contact.getId() == null) {
            contactService.save(contact);
        } else {
            contactService.update(contact);
        }
        return "redirect:/";
    }

    @GetMapping("/contact/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.findById(id);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "create";
        }
        return "redirect:/";
    }

    @GetMapping("/contact/delete/{id}")
    public String delete(@PathVariable Long id) {
        contactService.delete(id);
        return "redirect:/";
    }

}
