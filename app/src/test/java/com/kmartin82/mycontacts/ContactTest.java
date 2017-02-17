package com.kmartin82.mycontacts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;





public class ContactTest {
    @Test
    public void contactNameTest() {
        String name = "testname";
        Contact contact = new Contact();
        contact.setMname(name);
        assertEquals(name, contact.getMname());


    }

    @Test
    public void contactEmailTest() {
        String email = "test@testweb.net";
        Contact contact = new Contact();
        contact.setMname(email);
        assertEquals(email, contact.getMname());
    }

    @Test
    public void UUIDTest() {
        Contact contact = new Contact();
        assertNotNull(contact.getID());
    }
}




