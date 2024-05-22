package com.example.contacts2.service;

import com.example.contacts2.model.Contact;
import com.example.contacts2.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService{

    private final ContactRepository contactRepository;
    public List<Contact> findAll(){
        return contactRepository.findAll();
    }

    public Contact findById(Long id){
        return contactRepository.findById(id);
    }

    @Override
    public Contact add(Contact contact) {
        return contactRepository.add(contact);
    }

    public Contact update(Contact contact){
        return contactRepository.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        contactRepository.deleteById(id);

    }

    @Override
    public void batchInsert(List<Contact> contactList) {
        contactRepository.batchInsert(contactList);

    }
}
