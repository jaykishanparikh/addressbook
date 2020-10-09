package com.pwc.addressbook.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
public class Contact implements Serializable, Comparable<Contact> {
    String name;
    String phoneNumber;

    @Override
    public int compareTo(Contact o) {
        return name.compareTo(o.name);
    }
}
