package com.pwc.addressbook.repository;

import com.pwc.addressbook.model.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AddressBookRepositoryTest {

    @Autowired
    AddressBookRepository addressBookRepository;

    Set<Contact> contacts;

    @BeforeEach
    void setUp() {
        contacts = new HashSet<>();
        contacts.add(new Contact("Sandy", "0444333222"));
        contacts.add(new Contact("Andy", "0444999888"));
        contacts.add(new Contact("Jane", "0444777666"));
        contacts.add(new Contact("Boby", "0434876967"));
        contacts.add(new Contact("Jay", "0438743289"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addUserSuccess() {
        Set<Contact> emptyAddressBook = addressBookRepository.addUser("book3");
        assertEquals(emptyAddressBook.size(), 0);
    }

    @Test
    void addContactSuccess() {
        assertEquals(addressBookRepository.addContact("book1", new Contact("Jay", "0438743289")), contacts);
    }

    @Test
    void addContactUnSuccess() {
        Set<Contact> updatedAddressBook = addressBookRepository.addContact("book1", new Contact("Sandy", "0444333222"));
        assertEquals(updatedAddressBook, contacts);
        assertEquals(updatedAddressBook.size(), 5);
    }

    @Test
    void getAllContactsSuccess() {
        Set<Contact> addressBook = addressBookRepository.getAllContacts("book1");
        assertEquals(addressBook.size(), 4);
    }
}