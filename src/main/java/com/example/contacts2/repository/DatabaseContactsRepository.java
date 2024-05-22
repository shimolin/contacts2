package com.example.contacts2.repository;

import com.example.contacts2.exception.ContactNotFoundException;
import com.example.contacts2.model.Contact;
import com.example.contacts2.repository.mapper.ContactsRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
//@Primary
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactsRepository implements ContactRepository{

    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Contact> findAll() {
        log.debug("Calling DatabaseContactsRepository.findAll");
        String sql = "SELECT * FROM contacts";
        return jdbcTemplate.query(sql, new ContactsRowMapper());
    }

    @Override
    public Contact findById(Long id) {
        log.debug("Calling DatabaseContactsRepository.findById with ID: {}", id);
        String sql = "SELECT * FROM contacts WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactsRowMapper(), 1)
                )
        );
        return contact;
    }

    @Override
    public Contact add(Contact contact) {
        log.debug("Calling DatabaseContactsRepository.add with Contact: {}", contact);
        contact.setId(System.currentTimeMillis());
        String sql = "INSERT INTO contacts (id, firstname, lastname, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getId(), contact.getFirstname(), contact.getLastname(), contact.getEmail(), contact.getPhone());
        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Calling DatabaseContactsRepository.update with Contact: {}", contact);
        Contact existedContact = findById(contact.getId());
        if(existedContact != null){
            String sql = "UPDATE contacts SET firstname = ?, lastname = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql, contact.getFirstname(), contact.getLastname(), contact.getEmail(), contact.getPhone(), contact.getId());
            return contact;
        }
        log.warn("Contact with ID {} not found", contact.getId());
        throw new ContactNotFoundException("Contact for update not found! ID: " + contact.getId());
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling DatabaseContactsRepository.delete with ID: {}",id);
        String sql = "DELETE FROM contacts WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public void batchInsert(List<Contact> contactList) {
        log.debug("Calling DatabaseContactsRepository.bacthInsert");
        String sql = "INSERT INTO contacts (id, firstname, lastname, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Contact contact = contactList.get(i);
                ps.setLong(1, contact.getId());
                ps.setString(2, contact.getFirstname());
                ps.setString(3, contact.getLastname());
                ps.setString(4, contact.getEmail());
                ps.setString(5, contact.getPhone());
            }

            @Override
            public int getBatchSize() {
                return contactList.size();
            }
        });


    }
}
