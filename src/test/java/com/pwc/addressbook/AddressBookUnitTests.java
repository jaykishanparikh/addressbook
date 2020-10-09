package com.pwc.addressbook;

import com.pwc.addressbook.model.AddressBookData;
import com.pwc.addressbook.repository.AddressBookRepository;
import com.pwc.addressbook.service.AddressBookService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AddressBookUnitTests {
    @Mock
    private AddressBookRepository repository;

    @Mock
    private AddressBookService service;

    @Mock
    private ServerRequest serverRequest;

    @Mock
    private Mono<ServerResponse> serverResponseMono;

    private AddressBookData addressBookData = AddressBookData.getInstance();

    @BeforeAll
    public static void setup() {
    }

    @AfterAll
    public static void tearDown() {

    }

    @Test
    public void testInitData() {

        assertNotNull(addressBookData);
        assertNotNull(addressBookData.getUserMap());
        assertEquals(addressBookData.getUserMap().size(), 2);

        assertNotNull(addressBookData.getUserMap().get("book1"));
        assertEquals(addressBookData.getUserMap().get("book1").size(), 4);

        assertNotNull(addressBookData.getUserMap().get("book2"));
        assertEquals(addressBookData.getUserMap().get("book2").size(), 3);
    }
}
