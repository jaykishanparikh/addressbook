package com.pwc.addressbook.service;

import com.pwc.addressbook.exception.ErrorResponse;
import com.pwc.addressbook.model.Contact;
import com.pwc.addressbook.repository.AddressBookRepository;
import com.pwc.addressbook.utils.AddressBookUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;


@Service
public class AddressBookService {
    private static final Logger LOG = LoggerFactory.getLogger(AddressBookService.class);

    @Autowired
    AddressBookRepository repository;

    public Set<Contact> addUser(String userName) {
        return repository.addUser(userName);
    }

    public Set<Contact> addContacts(String userName, List<Contact> contacts) {
        return repository.addContacts(userName, contacts);
    }

    public Mono<ServerResponse> getAllContacts(ServerRequest request) {
        String userNameParam = request.pathVariable("userName");
        LOG.info(String.format("AddressBookService :: getAllContacts. Input Param [%s]", userNameParam));
        Set<Contact> response = repository.getAllContacts(userNameParam);

        if (response == null || response.isEmpty())
            return ServerResponse.status(HttpStatus.BAD_REQUEST).body(fromValue(
                    new ErrorResponse(String.format("No address book found for user : %s", userNameParam), "400")));
        else
            return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(response));

    }

    public Mono<ServerResponse> getUniqueContacts(ServerRequest request) {
        String firstUser = request.pathVariable("userName1");
        String secondUser = request.pathVariable("userName2");
        LOG.info(String.format("AddressBookService :: getUniqueContacts. Input Param [%s, %s]", firstUser, secondUser));
        Set<Contact> firstAddressBook = repository.getAllContacts(firstUser);
        Set<Contact> secondAddressBook = repository.getAllContacts(secondUser);

        Set<Contact> uniqueContactSet = AddressBookUtils.getMutuallyExclusiveItems(firstAddressBook, secondAddressBook);
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(uniqueContactSet));
    }

    public Mono<ServerResponse> addContact(ServerRequest request) {
        LOG.info("AddressBookService :: addContact");
        String userNameParam = request.pathVariable("userName");
        LOG.info(String.format("AddressBookService :: addContact. Input Param [%s]", userNameParam));
        return request.bodyToMono(Contact.class)
                .flatMap(person -> {
                    Set<Contact> response = repository.addContact(userNameParam, person);
                    return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(response));
                });
    }
}
