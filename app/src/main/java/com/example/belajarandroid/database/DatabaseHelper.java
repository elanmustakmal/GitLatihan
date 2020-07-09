package com.example.belajarandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "loginSQLite.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id integer PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE user(id integer PRIMARY KEY AUTOINCREMENT, username text, password text)");
        db.execSQL("INSERT INTO session(id, login) VALUES(i, kosong)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
    public Boolean cekSession(String sessionValue){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE login = ?", new String[]{sessionValue});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }
    public Boolean upgradeSession(String sessionValue, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", sessionValue);
        long update = db.update("session", contentValues, "id = " +id, null);
        if (update == -1){
            return false;
        } else {
            return true;
        }
    }
    public Boolean insertUser(String username, String password, String email, String name, String roles, String aktif, String nonAktif){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("roles", String.valueOf(roles));
        contentValues.put("aktif", String.valueOf(aktif));
        contentValues.put("nonAktif", String.valueOf(nonAktif));
        long insert = db.insert("user", null, contentValues);
        if (insert == -1){
            return false;
        } else {
            return true;
        }

    }
    public Boolean cekLogin(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ? AND email = ? AND name = ? AND roles = ? AND aktif = ?", new String[]{username, password});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }
}
