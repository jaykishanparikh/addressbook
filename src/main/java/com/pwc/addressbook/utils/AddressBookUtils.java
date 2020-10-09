package com.pwc.addressbook.utils;

import com.pwc.addressbook.model.Contact;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddressBookUtils {

    public static Set<Contact> getMutuallyExclusiveItems(Set<Contact> contacts1, Set<Contact> contacts2) {
        Set<Contact> mergedSet = new HashSet<>();
        Set<String> names = contacts2.stream()
                .map(Contact::getName)
                .collect(Collectors.toSet());
        Set<Contact> uniqueContacts = contacts1.stream()
                .filter(contact -> !names.contains(contact.getName()))
                .collect(Collectors.toSet());

        Set<String> names2 = contacts1.stream()
                .map(Contact::getName)
                .collect(Collectors.toSet());
        Set<Contact> uniqueContacts2 = contacts2.stream()
                .filter(contact -> !names2.contains(contact.getName()))
                .collect(Collectors.toSet());

        mergedSet.addAll(uniqueContacts);
        mergedSet.addAll(uniqueContacts2);

        return mergedSet;
    }
}
