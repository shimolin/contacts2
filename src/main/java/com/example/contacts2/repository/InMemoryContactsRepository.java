package com.example.contacts2.repository;

import com.example.contacts2.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryContactsRepository  implements ContactRepository{

    private Map<Long, Contact> contactList = new HashMap<>();

    public InMemoryContactsRepository() {
        contactList.put(1L, new Contact(1L, "Ivan", "Ivan", "ivanov@email.com","+79241234578"));
    }

    @Override
    public List<Contact> findAll() {
        return contactList.values().stream().toList();
    }

    @Override
    public Contact findById(Long id) {
        return contactList.get(id);
    }

    @Override
    public Contact add(Contact contact) {
        Long id = System.currentTimeMillis();
        return contactList.put(id, new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone()));
    }

    @Override
    public Contact update(Contact contact) {
        Contact existContact = findById(contact.getId());
        if(existContact != null){
            existContact.setFirstName(contact.getFirstName());
            existContact.setLastName(contact.getLastName());
            existContact.setEmail(contact.getEmail());
            existContact.setPhone(contact.getPhone());
        }
        return existContact;
    }

    @Override
    public void deleteById(Long id) {
        contactList.remove(id);
    }

    @Override
    public void batchInsert(List<Contact> contactList) {
        for(Contact contact : contactList){
            this.contactList.put(contact.getId(), contact);
        }
    }
}
