package com.pwc.addressbook.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pwc.addressbook.service.AddressBookService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

@Getter
@Setter
@Component
public class AddressBookData {
    private static AddressBookData instance;
    private Map<String, SortedSet<Contact>> userMap = new HashMap();

    @Autowired
    AddressBookService service;

    private AddressBookData (){
    }

    public static AddressBookData getInstance () {
        if (instance == null) {
            instance = new AddressBookData();
        }
        return instance;
    }

    @PostConstruct
    public void initData () {
        Gson gson = new GsonBuilder().create();
        try {
            String book1Content = new String(Files.readAllBytes(new ClassPathResource("/static/book1-data.json").getFile().toPath()));
            String book2Content = new String(Files.readAllBytes(new ClassPathResource("/static/book2-data.json").getFile().toPath()));
            service.addUser("book1");
            service.addContacts("book1", Arrays.asList(gson.fromJson(book1Content, Contact[].class)));
            service.addUser("book2");
            service.addContacts("book2", Arrays.asList(gson.fromJson(book2Content, Contact[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
