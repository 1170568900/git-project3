package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private MyDataBase myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putInt();
        editor.putString();
        editor.putBoolean();
        editor.apply();
        SharedPreferences sharedPreference=getSharedPreferences("data",MODE_PRIVATE);
        String data1=sharedPreference.getString();
        int data2=sharedPreference.getInt("age",0);

        myDataBase=new MyDataBase(this,"Book.db",null,1);
        myDataBase.getWritableDatabase();
        SQLiteDatabase sqLiteDatabase=myDataBase.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("author","123");
        contentValues.clear();
        contentValues.put("author","456");
        contentValues.clear();
        sqLiteDatabase.insert("Book",null,contentValues);
        sqLiteDatabase.update("Book",contentValues,"author=?",new String[]{"789"});
        sqLiteDatabase.delete("Book","author=?",new String[]{"123"});
        Cursor cursor=sqLiteDatabase.query("Book",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("author"));
            }while(cursor.moveToNext());
        }
        cursor.close();
        //|name-----|
        //|??????1-----|
        //|??????2----|
        //|??????3----|

        /*
        1.build????????????   implementation 'org.litepal.guolindev:core:3.2.3'
        2.main???????????????asserts????????????litepal.xml??????
        <litepal>
        <dbname value="BookStore"/>
        <version value="1"/>
        <list>
        <mapping class="?????????"
        </list>
        3.manifest?????????name
        "org.litepal.LitePalApplication"
        4.???????????????????????????

        ???????????????
        Litepal.getDatabase
        ???????????? ???????????????LitePalSupport
        ??????book.setName="abc" book.save
        ?????? LitePal.deleteAll(Book.class,"price < ?","15");
        ??????set->set ??? book.updata("name=?"and"author=?","b","a")
        ???:list<book> books=Litepal.findAll(Book.class)
        for(Book book:books){book.getName();
        }
        select ???????????????
        where ????????????
        order ???????????? desc?????? asc ??????
        limit ??????????????????
        offset ?????????







         */

    }
    //input=edit.getText.toString()
    public void save(String input){
        FileOutputStream fileOutputStream=null;
        BufferedWriter bufferedWriter=null;
        try{
            fileOutputStream=openFileOutput("data", Context.MODE_PRIVATE);
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(input);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(bufferedWriter!=null){
                    bufferedWriter.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void load() {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fileInputStream = openFileInput("data");
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }


}

