package com.kmartin82.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.kmartin82.mycontacts.database.ContactBaseHelper;
import com.kmartin82.mycontacts.database.ContactCursorWrapper;
import com.kmartin82.mycontacts.database.ContactDbSchema;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddressBook {
    private static AddressBook sAddressBook;
    private SQLiteDatabase mDatabase;

    private static ContentValues getContentValues(Contact contact){
        byte[] imageData = {};
        if (contact.getmImage() != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            contact.getmImage().compress(Bitmap.CompressFormat.PNG, 0, stream);
            imageData = stream.toByteArray();
        }

        ContentValues values = new ContentValues();
        values.put(ContactDbSchema.ContactTable.Cols.UUID, contact.getID().toString());
        values.put(ContactDbSchema.ContactTable.Cols.NAME, contact.getmName());
        values.put(ContactDbSchema.ContactTable.Cols.EMAIL, contact.getmEmail());
        values.put(ContactDbSchema.ContactTable.Cols.FAVORITE, contact.isFavorite() ? "true" : "false");
        values.put(ContactDbSchema.ContactTable.Cols.ADDRESS, contact.getAddress());
        values.put(ContactDbSchema.ContactTable.Cols.IMAGE, imageData);

        return values;
    }

    private AddressBook(Context context){
        mDatabase = new ContactBaseHelper(context).getWritableDatabase();
    }

    public static AddressBook get(Context context){
        if (sAddressBook == null){
            sAddressBook = new AddressBook(context);
        }
        return sAddressBook;
    }

    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        ContactCursorWrapper cursorWrapper = query(null, null);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                contacts.add(cursorWrapper.getContact());
                cursorWrapper.moveToNext();
            }
        }
        finally {
            cursorWrapper.close();
        }
        return contacts;
    }

    public Contact getContact(UUID id) {
        ContactCursorWrapper cursorWrapper = query(
                ContactDbSchema.ContactTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursorWrapper.getCount() == 0) {
                return null;
            }

            cursorWrapper.moveToFirst();
            return cursorWrapper.getContact();
        }
        finally {
            cursorWrapper.close();
        }
    }

    public List<Contact> GetFavoriteContacts(){
        List<Contact> contacts = new ArrayList<>();

        ContactCursorWrapper cursorWrapper = query(
                ContactDbSchema.ContactTable.Cols.FAVORITE + " = ?",
                new String[] { "true" }
        );
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                contacts.add(cursorWrapper.getContact());
                cursorWrapper.moveToNext();
            }
        }
        finally {
            cursorWrapper.close();
        }
        return contacts;
    }

    public void add(Contact contact) {
        ContentValues values = getContentValues(contact);
        mDatabase.insert(ContactDbSchema.ContactTable.NAME, null, values);
    }

    public void updateContact(Contact contact) {
        String uuidString = contact.getID().toString();
        ContentValues values = getContentValues(contact);
        mDatabase.update(ContactDbSchema.ContactTable.NAME, values,
                ContactDbSchema.ContactTable.Cols.UUID  + " = ?",
                new String[] { uuidString });
    }

    public ContactCursorWrapper query(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(ContactDbSchema.ContactTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new ContactCursorWrapper(cursor);
    }


}
