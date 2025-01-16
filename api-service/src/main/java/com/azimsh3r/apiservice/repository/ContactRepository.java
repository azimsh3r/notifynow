package com.azimsh3r.apiservice.repository;

import com.azimsh3r.apiservice.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> getContactsByUploadedById(int id);
}
