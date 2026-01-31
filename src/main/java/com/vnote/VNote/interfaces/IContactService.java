package com.vnote.VNote.interfaces;

import com.vnote.VNote.modules.contact.Contact;
import com.vnote.VNote.modules.contact.ContactInputDTO;

public interface IContactService {
    public boolean healthCheck();
    public Contact addContact(ContactInputDTO contactData);
}
