package com.pwc.addressbook.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pwc.addressbook.model.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class AddressBookIntegrationTest {

    @Value("${get.all.contacts.url}")
    private String getAllContacts;

    @Value("${add.contact.url}")
    private String addContact;

    @Value("${get.unique.contacts.url}")
    private String getUniqueContacts;

    Contact contact;

    @Autowired
    private ApplicationContext applicationContext;

    private WebTestClient testClient;

    private Gson gson;

    @BeforeEach
    void setUp() {
        testClient = WebTestClient.bindToApplicationContext(applicationContext).build();
        contact = new Contact("Rose", "0432986459");
        gson = new GsonBuilder().create();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void successResponseGetAllContacts() {
        EntityExchangeResult<String> response =
                testClient.get().uri(getAllContacts, "book1")
                        .exchange().expectStatus()
                        .is2xxSuccessful().expectBody(String.class).returnResult();

        String successResponse = response.getResponseBody();
        assertNotEquals(successResponse, null);

        List<Contact> addressBook = Arrays.asList(gson.fromJson(successResponse, Contact[].class));
        assertEquals(addressBook.size(), 4);
    }

    @Test
    public void successAddContact() {
        EntityExchangeResult<String> response =
                testClient.post().uri(addContact, "book1")
                        .header("Content-Type", "application/json")
                        .body(Mono.just(contact), Contact.class)
                        .exchange().expectStatus()
                        .is2xxSuccessful().expectBody(String.class).returnResult();

        String successResponse = response.getResponseBody();
        assertNotEquals(successResponse, null);

        List<Contact> addressBook = Arrays.asList(gson.fromJson(successResponse, Contact[].class));
        assertEquals(addressBook.size(), 5);
        assertEquals(addressBook.get(3).getName(), "Rose");
    }

    @Test
    public void successGetUniqueContacts() {
        EntityExchangeResult<String> response =
                testClient.get().uri(getUniqueContacts, "book1", "book2")
                        .exchange().expectStatus()
                        .is2xxSuccessful().expectBody(String.class).returnResult();

        String successResponse = response.getResponseBody();
        assertNotEquals(successResponse, null);

        List<Contact> addressBook = Arrays.asList(gson.fromJson(successResponse, Contact[].class));
        assertEquals(addressBook.size(), 3);
        assertEquals(addressBook.get(0).getName(), "Kavi");
    }

}
