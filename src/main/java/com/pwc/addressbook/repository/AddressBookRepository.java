package com.pwc.addressbook.repository;

import com.pwc.addressbook.model.AddressBookData;
import com.pwc.addressbook.model.Contact;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AddressBookRepository {
    public Set<Contact>  addUser (String userName) {
        AddressBookData.getInstance().getUserMap().put(userName, new TreeSet<Contact>());
        return AddressBookData.getInstance().getUserMap().get(userName);
    }

    public Set<Contact> addContacts (String userName, List<Contact> contacts) {
        SortedSet<Contact> contactSet =  AddressBookData.getInstance().getUserMap().get(userName);
        contacts.stream().forEach ((contact)-> contactSet.add(contact) );
        AddressBookData.getInstance().getUserMap().put(userName, contactSet);
        return contactSet;
    }

    public Set<Contact> addContact (String userName, Contact contact) {
        SortedSet<Contact> contactSet =  AddressBookData.getInstance().getUserMap().get(userName);
        contactSet.add(contact);
        AddressBookData.getInstance().getUserMap().put(userName, contactSet);
        return contactSet;
    }

    public Set<Contact> getAllContacts (String userName) {
        return  AddressBookData.getInstance().getUserMap().get(userName);
    }
}
