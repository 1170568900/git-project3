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
        //|数据1-----|
        //|数据2----|
        //|数据3----|

        /*
        1.build添加依赖   implementation 'org.litepal.guolindev:core:3.2.3'
        2.main目录下创建asserts目录创建litepal.xml文件
        <litepal>
        <dbname value="BookStore"/>
        <version value="1"/>
        <list>
        <mapping class="实体类"
        </list>
        3.manifest中配置name
        "org.litepal.LitePalApplication"
        4.创建实体类形成映射

        创建数据库
        Litepal.getDatabase
        增删改查 实体类继承LitePalSupport
        增：book.setName="abc" book.save
        删： LitePal.deleteAll(Book.class,"price < ?","15");
        改：set->set 或 book.updata("name=?"and"author=?","b","a")
        查:list<book> books=Litepal.findAll(Book.class)
        for(Book book:books){book.getName();
        }
        select 查询列数据
        where 约束条件
        order 默认升序 desc降序 asc 升序
        limit 限定查询数量
        offset 偏移量







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

