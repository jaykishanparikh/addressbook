package com.pwc.addressbook.config;

import com.pwc.addressbook.model.Contact;
import com.pwc.addressbook.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Slf4j
@Configuration
public class AddressBookRouterConfig {
    private static final Logger LOG = LoggerFactory.getLogger(AddressBookRouterConfig.class);

    @Value("${get.all.contacts.url}")
    private String getAllContacts;

    @Value("${add.contact.url}")
    private String addContact;

    @Value("${get.unique.contacts.url}")
    private String getUniqueContacts;

    @Bean
    public RouterFunction<ServerResponse> getAllContacts (AddressBookService addressBookService) {
        LOG.info(String.format("AddressBookROuterConfig :: getAllContacts"));

        return RouterFunctions.route(
                GET(getAllContacts).and(accept(MediaType.APPLICATION_JSON)),
                addressBookService::getAllContacts);
    }

    @Bean
    public RouterFunction<ServerResponse> getUniqueContacts (AddressBookService addressBookService) {
        LOG.info(String.format("AddressBookROuterConfig :: getUniqueContacts"));
        return RouterFunctions.route(
                GET(getUniqueContacts).and(accept(MediaType.APPLICATION_JSON)),
                addressBookService::getUniqueContacts);
    }

    @Bean
    public RouterFunction<ServerResponse> addContact (AddressBookService addressBookService) {
        LOG.info(String.format("AddressBookROuterConfig :: addContact"));
        return RouterFunctions.route(
                POST(addContact).and(accept(MediaType.APPLICATION_JSON)),
                addressBookService::addContact);
    }
}
