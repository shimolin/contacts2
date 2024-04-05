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
    public void save(Contact contact){
        contactRepository.add(contact);
    }

    public void update(Contact contact){
        contactRepository.update(contact);
    }

    public void delete(Long id){
        contactRepository.deleteById(id);
    }
}
