package com.example.userdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by H on 2015-08-20.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME = "userInfoManager";
    //user info table name
    private static final String TABLE_USERS = "userInfo";
    //user info table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_FOOT_SIZE = "foot_size";
    private static final String KEY_LEG_LENGTH = "leg_length";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERINFO_TABLE =
                "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_HEIGHT + " TEXT, "
                + KEY_FOOT_SIZE + " TEXT, "
                + KEY_LEG_LENGTH + " TEXT" + ")";
        db.execSQL(CREATE_USERINFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new user info
    public void addUserInfo(UserInfo userinfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, userinfo.getName());
        values.put(KEY_HEIGHT, userinfo.getHeight());
        values.put(KEY_FOOT_SIZE, userinfo.getFootSize());
        values.put(KEY_LEG_LENGTH, userinfo.getLegLength());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single user info
    public UserInfo getUserInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_NAME, KEY_HEIGHT, KEY_FOOT_SIZE, KEY_LEG_LENGTH}, KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        UserInfo userinfo = new UserInfo(
                Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return userinfo;
    }

    public UserInfo getUserInfo(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_NAME, KEY_HEIGHT, KEY_FOOT_SIZE, KEY_LEG_LENGTH}, KEY_NAME + "=?",
                new String[]{name},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        UserInfo userinfo = new UserInfo(
                Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return userinfo;
    }

    public int getUserID(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_NAME, KEY_HEIGHT, KEY_FOOT_SIZE, KEY_LEG_LENGTH}, KEY_NAME + "=?",
                new String[]{name},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        int userid = Integer.parseInt(cursor.getString(0));
        // return contact
        return userid;
    }

    // Getting all user info
    public List<UserInfo> getAllUserInfo() {
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserInfo userinfo = new UserInfo();
                userinfo.setID(Integer.parseInt(cursor.getString(0)));
                userinfo.setName(cursor.getString(1));
                userinfo.setHeight(cursor.getString(2));
                userinfo.setFootSize(cursor.getString(3));
                userinfo.setLegLength(cursor.getString(4));
                // Adding contact to list
                userInfoList.add(userinfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return userInfoList;
    }

    // Getting all user name
    public List<String> getAllUserName() {
        List<String> userNameList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT " + KEY_NAME +" FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(0);
                // Adding names to list
                userNameList.add(username);
            } while (cursor.moveToNext());
        }

        // return user name list
        return userNameList;
    }

    // Updating single contact
    public int updateUserInfo(UserInfo userinfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, userinfo.getName());
        values.put(KEY_HEIGHT, userinfo.getHeight());
        values.put(KEY_FOOT_SIZE, userinfo.getFootSize());
        values.put(KEY_LEG_LENGTH, userinfo.getLegLength());

        // updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(userinfo.getID())});
    }

    // Deleting single user info
    public void deleteUserInfo(UserInfo userinfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[]{String.valueOf(userinfo.getID())});
        db.close();
    }

    public void deleteUserInfo(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[] { String.valueOf(userid) });
        db.close();
    }


    // Getting user info count
    public int getUserInfoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
