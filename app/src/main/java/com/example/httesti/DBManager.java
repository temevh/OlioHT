package com.example.httesti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class DBManager extends SQLiteOpenHelper {
    public static final String DBNAME = "Users.db";

    public DBManager(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        // not the most secure way to store things into a DB but works for this project.
        MyDB.execSQL("create Table users(username TEXT primary key, hash TEXT, salt TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        try{
            byte[] salt = generateSalt();
            String hash = generateHashedPW(password,salt);
            contentValues.put("username", username);
            contentValues.put("hash", hash);
            contentValues.put("salt", salt);
            long result = MyDB.insert("users", null, contentValues);

            if(result==-1)
                return false;
            else
                return true;
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkpassword(String username, String password){

        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select hash, salt from users where username = ?", new String[] {username});
        if(cursor != null && cursor.moveToFirst()) {
            System.out.println(cursor.getCount());
            String hash = cursor.getString(0);
            byte[] salt = cursor.getBlob(1);
            // Checking if given password hashed with salt from DB equals the hash in the DB
            if (generateHashedPW(password, salt).equals(hash))
                return true;
            else
                return false;
        }
        return false;
    }


    // SHA-512 implementation
    public String generateHashedPW(String password, byte[] salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // generateSalt()-method generates salt, in this case it's 16 bytes of salt
    private byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom rand = new SecureRandom();
        byte[] salt = new byte[16];
        rand.nextBytes(salt);
        return salt;
    }

}