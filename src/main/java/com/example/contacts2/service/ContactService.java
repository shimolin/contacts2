package com.example.contacts2.service;

import com.example.contacts2.model.Contact;

import java.util.List;

public interface ContactService{
    public List<Contact> findAll();
    public Contact findById(Long id);
    public Contact add(Contact contact);
    public Contact update(Contact contact);
    public void deleteById(Long id);
    public void batchInsert(List<Contact> contactList);

}
