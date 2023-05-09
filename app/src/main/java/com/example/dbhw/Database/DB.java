package com.example.dbhw.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private Context context;
    String TBLName = "student";

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBLName + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, age TEXT, address TEXT NOT NULL UNIQUE);";
        db.execSQL(sql);
    }

    public long insertData(String name, int age, String phone) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("address", phone);
        try {
            return database.insertWithOnConflict(TBLName, null, contentValues, SQLiteDatabase.CONFLICT_NONE);
        } catch (Exception e){
            return -1L;
        }
    }

    public Cursor searchData() {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "SELECT * FROM " + TBLName + ";";
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }

    public Cursor keywordSearchData(String keyword) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "SELECT * FROM " + TBLName + " WHERE name = '"+keyword+"';";
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }

    public void onUpdate(String keyword, String name, String age, String address) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE "+TBLName+" SET name = '"+name+"', age = "+Integer.parseInt(age)+", address = '"+address+"' WHERE name = '"+keyword+"';";
        database.execSQL(sql);
    }
    public void onDelete(String keyword) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM " + TBLName + " WHERE name = '" + keyword +"';";
        database.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TBLName + ";";
        db.execSQL(sql);
        onCreate(db);
    }
}
