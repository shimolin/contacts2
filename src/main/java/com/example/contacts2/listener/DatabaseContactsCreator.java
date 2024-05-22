package com.example.contacts2.listener;

import com.example.contacts2.model.Contact;
import com.example.contacts2.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactsCreator {
    private final ContactService contactService;

//    @EventListener(ApplicationStartedEvent.class)
    public void createContactsData() {
        log.debug("Calling DatabaseContactsCreator.createContactsData...");
        List<Contact> contactList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            Long id = (long) ((i + 1));
            contact.setId(id);
            contact.setFirstname("FN" + id);
            contact.setLastname("LN" + id);
            contact.setEmail(contact.getFirstname() + "@mail.ru");
            contact.setPhone("+79124567890");
            contactList.add(contact);
        }
        contactService.batchInsert(contactList);
    }
}
