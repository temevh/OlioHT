package com.example.httesti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class DBManager extends SQLiteOpenHelper {
    // name of the DB that gets created
    public static final String DBNAME = "Users.db";

    // constructor for the class
    public DBManager(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        // Table for users login info
        // (not the most secure way to store things in a DB but works for this project.)
        MyDB.execSQL("create Table users(username TEXT primary key, " +
                                        "hash TEXT, " +
                                        "salt TEXT);");
        // Creating the profile table
        MyDB.execSQL("create Table profiles(" +
                        "username TEXT, " +
                        "user BLOB," +
                        "FOREIGN KEY(username) REFERENCES users(username)" +
                        "ON DELETE CASCADE);");
    }

    @Override
    // needed if the table needs to be recreated
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists profiles");
    }

    public Boolean insertProfile(User user){ // method for inserting users profiles
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", user.getUsername());
        try {
            //open baos and oos so that the user-object can be turned into a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(user);
            // turn the user into a byte array for storing into the database as a BLOB
            byte[] userAsBytes = baos.toByteArray();

            contentValues.put("user", userAsBytes);

            long result = MyDB.insert("profiles", null, contentValues);

            if(result==-1){
                MyDB.close();
                return false;
            }

            else{
                MyDB.close();
                return true;
            }

        } catch (IOException e) {
            MyDB.close();
            e.printStackTrace();
            return false;
        }finally {
            MyDB.close();
            System.out.println("User profile created!");
        }


    }



    public Boolean insertUser(String username, String password) { // method for inserting users login data
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        try{
            // hash the users password and store the hash and the salt with the username into the DB-table
            byte[] salt = generateSalt();
            String hash = generateHashedPW(password,salt);
            contentValues.put("username", username);
            contentValues.put("hash", hash);
            contentValues.put("salt", salt);
            long result = MyDB.insert("users", null, contentValues);

            if(result==-1){
                MyDB.close();
                return false;
            }

            else{
                MyDB.close();
                return true;
            }


        }catch (NoSuchAlgorithmException e) {
            MyDB.close();
            e.printStackTrace();
            return false;
        }
    }


    public User fetchUser(String username){ // method for fetching a spesific user-object from the DB
        User user = null;
        SQLiteDatabase MyDB = this.getReadableDatabase();
        // find the user-object of the user that has logged in
        Cursor cursor = MyDB.rawQuery("Select user from profiles where username = ?", new String[]{username});
        if(cursor != null && cursor.moveToFirst()) {
            try {
                //turn the byte array into a user-object
                byte[] userAsBytes = cursor.getBlob(0);
                ByteArrayInputStream bais = new ByteArrayInputStream(userAsBytes);
                ObjectInputStream ois = null;
                ois = new ObjectInputStream(bais);
                user = (User) ois.readObject();
                ois.close();
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        MyDB.close();
        return user;
    }


    public void updateUser(User user){ // method for updating users info (for example favourites array)
        SQLiteDatabase MyDB = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        String username = user.getUsername();
        try {
            // update the users data in the profiles-table
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(user);

            byte[] userAsBytes = baos.toByteArray();

            contentValues.put("user", userAsBytes);

            long result = MyDB.update("profiles", contentValues, "username = ?", new String[]{username});

            if(result==-1){
                System.out.println("Something went wrong in update!");
                return;
            }
            baos.close();
            oos.close();
            MyDB.close();

        } catch (IOException e) {
            MyDB.close();
            e.printStackTrace();
        }finally {
            MyDB.close();
            System.out.println("Update completed!");
        }
    }

    public Boolean checkusername(String username, String table) { // method for checking if a username already exists in the DB
        if(table.equals("users")){ // checking if the username is already taken in the users-table
            SQLiteDatabase MyDB = this.getReadableDatabase();
            Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
            if (cursor.getCount() > 0){
                MyDB.close();
                cursor.close();
                return true;
            }
            else{
                MyDB.close();
                return false;
            }


        }
        if(table.equals("profiles")){// checking if the username is already taken in the profiles-table
            SQLiteDatabase MyDB = this.getReadableDatabase();
            Cursor cursor = MyDB.rawQuery("Select * from profiles where username = ?", new String[]{username});
            System.out.println(cursor.getCount());
            if (cursor.getCount() > 0){
                cursor.close();
                MyDB.close();
                return true;
            }
            else{
                MyDB.close();
                return false;
            }
        }
        return false;
    }

    public Boolean checkpassword(String username, String password){ // method for checking if the password matches

        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select hash, salt from users where username = ?", new String[] {username});
        if(cursor != null && cursor.moveToFirst()) {
            String hash = cursor.getString(0);
            byte[] salt = cursor.getBlob(1);
            // Checking if given password hashed with salt from DB equals the hash in the DB
            if (generateHashedPW(password, salt).equals(hash)){
                MyDB.close();
                cursor.close();
                return true;
            }

            else{
                MyDB.close();
                cursor.close();
                return false;
            }
        }
        cursor.close();
        MyDB.close();
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

    // generateSalt()-method generates salt, in this case it's 16 bytes of salt for hashing the passwords
    private byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom rand = new SecureRandom();
        byte[] salt = new byte[16];
        rand.nextBytes(salt);
        return salt;
    }

}