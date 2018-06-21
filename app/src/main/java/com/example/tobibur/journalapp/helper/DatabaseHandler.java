package com.example.tobibur.journalapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tobibur.journalapp.model.Journal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017-10-11.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "journalManager.db";

    // Contacts table name
    private static final String JOURNAL_TABLE = "journals";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_JOURNAL_DATA = "journal_data";
    private static final String KEY_DATE_TIME = "date_time";
    //private static final String KEY_SYNC_STATUS = "status";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_JOURNALS_TABLE = "CREATE TABLE " + JOURNAL_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_JOURNAL_DATA + " TEXT,"
                + KEY_DATE_TIME + " TEXT" + ")";
        db.execSQL(CREATE_JOURNALS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + JOURNAL_TABLE);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addPOST(Journal contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_JOURNAL_DATA, contact.get_Journal_data()); // Contact complaint_id
        values.put(KEY_DATE_TIME, contact.get_date_time()); // Contact user_id
		
        // Inserting Row
        db.insert(JOURNAL_TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Journal getPost(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(JOURNAL_TABLE, new String[] { KEY_ID,
                        KEY_JOURNAL_DATA, KEY_DATE_TIME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Journal contact = new Journal(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<Journal> getAllPosts() {
        List<Journal> postList = new ArrayList<Journal>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + JOURNAL_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Journal contact = new Journal();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.set_Journal_data(cursor.getString(1));
                contact.set_date_time(cursor.getString(2));
                // Adding contact to list
                postList.add(contact);
            } while (cursor.moveToNext());
        }

        // return post list
        return postList;
    }

    public String getdatabaseToString(){
        String datas="";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+JOURNAL_TABLE+ " WHERE 1";

        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(KEY_JOURNAL_DATA))!=null){
                datas+=c.getString(c.getColumnIndex(KEY_JOURNAL_DATA));
                datas+="\n";
            }
        }
        db.close();
        return datas;
    }

    // Updating single contact
    public int updateContact(Journal contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_JOURNAL_DATA, contact.get_Journal_data());
        values.put(KEY_DATE_TIME, contact.get_date_time());

        // updating row
        return db.update(JOURNAL_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Journal contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(JOURNAL_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getPostsCount() {
        String countQuery = "SELECT  * FROM " + JOURNAL_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
