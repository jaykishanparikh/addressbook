package com.pwc.addressbook.service;

import com.pwc.addressbook.config.AddressBookRouterConfig;
import com.pwc.addressbook.exception.AddreeBookException;
import com.pwc.addressbook.model.Contact;
import com.pwc.addressbook.repository.AddressBookRepository;
import com.pwc.addressbook.utils.AddressBookUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


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

        LOG.info(String.format("AddressBookService :: getUniqueContacts. Response set = ", response));
        if (response ==null || response.isEmpty())
            return ServerResponse.badRequest().body(
                    Mono.justOrEmpty(new AddreeBookException(String.format("No address book found for user : [%s]", userNameParam))),
                    AddreeBookException.class);
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
        LOG.info(String.format("AddressBookService :: getUniqueContacts. response records = ", uniqueContactSet));
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(uniqueContactSet));
    }

    public Mono<ServerResponse> addContact(ServerRequest request) {
        LOG.info(String.format("AddressBookService :: addContact"));
        String userNameParam = request.pathVariable("userName");
        LOG.info(String.format("AddressBookService :: addContact. Input Param [%s]", userNameParam));
        return request.bodyToMono(Contact.class)
                .flatMap(person -> {
                    Set<Contact> response = repository.addContact(userNameParam, person);
                    return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(response));
                });
    }
}
