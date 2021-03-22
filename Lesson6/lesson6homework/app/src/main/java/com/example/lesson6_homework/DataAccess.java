package com.example.lesson6_homework;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

public class DataAccess {
    private SQLiteDatabase db;
    private String DATABASE_NAME="app.db";
    private String TABLE_NAME="notes";


    public DataAccess(Context context){
        String s=context.getFilesDir().getPath();
        File file =new File(s+'/'+DATABASE_NAME);
        this.db = SQLiteDatabase.openOrCreateDatabase(file,null);
        //db.execSQL("DROP TABLE notes");
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (id INTEGER,note TEXT);",TABLE_NAME));
    }

    public String getNote(int id){
        String note="";
        Cursor query = db.rawQuery(String.format("SELECT * FROM %s WHERE id=?;",TABLE_NAME), new String[] {String.valueOf(id)});

        if(query.moveToNext())
        {
            note = query.getString(1);
        }
        query.close();
        return note;
    }
    public void saveNote(int id,String note){
        Cursor query = db.rawQuery(String.format("SELECT * FROM %s WHERE id=?;",TABLE_NAME), new String[] {String.valueOf(id)});
        if(query.moveToNext())
        {
            db.execSQL(String.format("UPDATE %s SET note=? where id=? ;",TABLE_NAME),new String[]{note,id+""});
        }  else{
            db.execSQL(String.format("INSERT INTO %s VALUES (?,?);",TABLE_NAME),new String[]{""+id,note});
        }
        query.close();


    }
}
