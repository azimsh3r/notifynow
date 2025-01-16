package com.azimsh3r.apiservice.service;

import com.azimsh3r.apiservice.model.Contact;
import com.azimsh3r.apiservice.model.User;
import com.azimsh3r.apiservice.repository.ContactRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ContactService {
    private final ContactRepository contactRepository;

    private final KafkaProducer kafkaProducer;

    public ContactService(ContactRepository contactRepository, KafkaProducer kafkaProducer) {
        this.contactRepository = contactRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(Integer id) {
        return contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found!"));
    }

    public List<Contact> getContactsByUploadedById(int id) {
        return contactRepository.getContactsByUploadedById(id);
    }

    public void deleteContact(Integer id) {
        contactRepository.deleteById(id);
    }

    public List<Contact> parseAndSaveContacts(MultipartFile file, User user) throws IOException {
        List<Contact> contacts = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            Contact contact = new Contact();
            contact.setName(getCellValue(row.getCell(0)));
            contact.setEmail(getCellValue(row.getCell(1)));
            contact.setPhoneNumber(getCellValue(row.getCell(2)));
            contact.setUploadedBy(user);
            contacts.add(contact);
        }

        workbook.close();
        return contactRepository.saveAll(contacts);
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() :
                cell.getCellType() == CellType.NUMERIC ? String.valueOf((int) cell.getNumericCellValue()) : null;
    }
}