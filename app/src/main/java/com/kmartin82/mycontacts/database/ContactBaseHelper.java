package com.kmartin82.mycontacts.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String FILENAME = "contactBase.db";

    public ContactBaseHelper(Context context) {
        super(context, FILENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE" + ContactDbSchema.ContactTable.NAME + "( " +
                "_id integer primary key autoincrement, " +
                ContactDbSchema.ContactTable.Cols.UUID + ", " +
                ContactDbSchema.ContactTable.Cols.NAME + ", " +
                ContactDbSchema.ContactTable.Cols.EMAIL + ", " +
                ContactDbSchema.ContactTable.Cols.FAVORITE + ", " +
                ContactDbSchema.ContactTable.Cols.ADDRESS + ", " +
                ContactDbSchema.ContactTable.Cols.IMAGE +
                ")";;
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
