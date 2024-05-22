package com.example.contacts2.repository;


import com.example.contacts2.exception.ContactNotFoundException;
import com.example.contacts2.jooq.Tables;

import com.example.contacts2.jooq.tables.records.ContactsRecord;
import com.example.contacts2.model.Contact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.Result;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
@Primary
@Slf4j
public class JooqContactsRepository implements ContactRepository {

    private final DSLContext dslContext;

    @Override
    public List<Contact> findAll() {
        log.debug("JooqContactsRepository.findAll");
        return dslContext.selectFrom(Tables.CONTACTS).fetchInto(Contact.class);
    }

    @Override
    public Contact findById(Long id) {
        log.debug("JooqContactsRepository.findById");
        return dslContext.selectFrom(Tables.CONTACTS).where(Tables.CONTACTS.ID.eq(id)).fetchInto(Contact.class).getFirst();
    }

    @Override
    public Contact add(Contact contact) {
        log.debug("JooqContactsRepository.add");
        contact.setId(System.currentTimeMillis());
        ContactsRecord contactsRecord = dslContext.newRecord(Tables.CONTACTS, contact);
        contactsRecord.store();
        return contactsRecord.into(Contact.class);
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("JooqContactsRepository.update");
        var maybeUpdateRecord = dslContext.update(Tables.CONTACTS)
                .set(dslContext.newRecord(Tables.CONTACTS, contact))
                .where(Tables.CONTACTS.ID.eq(contact.getId()))
                .returning()
                .fetchOptional();
        return maybeUpdateRecord.map(contactsRecord -> contactsRecord.into(Contact.class))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found ! ID " + contact.getId()));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("JooqContactsRepository.detete");
        dslContext.deleteFrom(Tables.CONTACTS).where(Tables.CONTACTS.ID.eq(id)).execute();

    }

    @Override
    public void batchInsert(List<Contact> contactList) {
        log.debug("JooqContactsRepository.batchInsert");
        List<Query> insertQueries = new ArrayList<>();
        for(Contact contact : contactList){
            insertQueries.add(
                    dslContext.insertInto(
                            Tables.CONTACTS,
                            Tables.CONTACTS.ID,
                            Tables.CONTACTS.FIRSTNAME,
                            Tables.CONTACTS.LASTNAME,
                            Tables.CONTACTS.EMAIL,
                            Tables.CONTACTS.PHONE
                    ).values(
                            contact.getId(),
                            contact.getFirstname(),
                            contact.getLastname(),
                            contact.getEmail(),
                            contact.getPhone()
                    )
            );
        }
        dslContext.batch(insertQueries).execute();
    }
}
