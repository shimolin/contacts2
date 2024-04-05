package com.example.contacts2.repository;

import com.example.contacts2.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {
    public List<Contact> findAll();
    public Contact findById(Long id);
    public Contact add(Contact contact);
    public Contact update(Contact contact);
    public void deleteById(Long id);
    public void batchInsert(List<Contact> contactList);
}
