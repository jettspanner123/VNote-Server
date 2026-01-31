package com.vnote.VNote.services;

import com.vnote.VNote.entities.ContactEntity;
import com.vnote.VNote.exceptions.ContactAlreadyExists;
import com.vnote.VNote.exceptions.UserNotFound;
import com.vnote.VNote.interfaces.IContactService;
import com.vnote.VNote.modules.contact.Contact;
import com.vnote.VNote.modules.contact.ContactInputDTO;
import com.vnote.VNote.repositories.ContactRepository;
import com.vnote.VNote.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public boolean healthCheck() {
        this.contactRepository.count();
        return true;
    }

    public Contact addContact(ContactInputDTO contactData) {
        final var user = this.userRepository.findById(contactData.userId).orElseThrow(() -> new UserNotFound("User id not found!"));
        final var doesContactExists = user.getContacts().stream().anyMatch(contact -> contact.getPhoneNumber().equals(contactData.phoneNumber));

        if (doesContactExists) throw new ContactAlreadyExists("Contact already exists for user id!");
        ContactEntity contact = new ContactEntity();
        contact.setPhoneNumber(contactData.phoneNumber);
        contact.setUser(user);
        contact.setEmail(contactData.email);
        contact.setFullName(contactData.fullName);
        this.contactRepository.save(contact);
        return Contact.fromEntity(contact);
    }

}
