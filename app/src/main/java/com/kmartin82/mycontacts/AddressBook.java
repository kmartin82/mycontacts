package com.kmartin82.mycontacts;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddressBook {
    private static AddressBook sAddressBook;
    private List<Contact> mContacts;

    private AddressBook(){
        mContacts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Contact contact = new Contact();
            contact.setmName("Person " + i);
            contact.setmEmail("Person" + i + "@email.com");
            contact.setAddress("550 E. Spring St, Columbus, OH 43215");

            // set every 10th as a favorite
            if (i % 5 == 0) {
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
