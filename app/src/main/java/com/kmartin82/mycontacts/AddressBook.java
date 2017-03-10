package com.kmartin82.mycontacts;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddressBook {
    private static AddressBook sAddressBook;
    private List<Contact> mContacts;

    private AddressBook(){
        mContacts = new ArrayList<>();
        for (int i=0; i<100; i++) {
            Contact contact = new Contact();
            contact.setMname("Person " + i);
            contact.setMemail("Person" + i + "@email.com");

            // set every 10th as a favorite
            if (i % 10 == 0) {
                contact.setFavorite(true);
            }

            mContacts.add(contact);
        }
    }

    public static AddressBook get(){
        if (sAddressBook == null){
            sAddressBook = new AddressBook();
        }
        return sAddressBook;
    }

    public List<Contact> getContacts() {
        return mContacts;
    }

    public Contact getContact(UUID id) {
        for (Contact contact: mContacts) {
            if (contact.getID().equals(id)) {
                return contact;
            }
        }
        return null;
    }
    public List<Contact> GetFavoriteContacts(){
        List<Contact> favorites = new ArrayList<>();
        for (Contact aContact: mContacts){
            if (aContact.isFavorite()){
             favorites.add(aContact);
            }
        }
        return favorites;
    }




}
