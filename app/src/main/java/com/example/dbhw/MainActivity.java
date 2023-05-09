package com.example.dbhw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dbhw.Database.DB;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database = null;
    final String dbName = "student.db";
    final String tableName = "student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB db = new DB(this, dbName, null, 1);
        db.getWritableDatabase();
        Button button = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        TextView textView = findViewById(R.id.view_query);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, UpActivity.class);
                startActivity(intent1);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, DeleteActivity.class);
                startActivity(intent2);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
//                Cursor cursor = db.keywordSearchData(binding.viewQuery.getText().toString());
                Cursor cursor = db.searchData();
                if (cursor.getCount() == 0) {
                    textView.setText("\n 데이터가 없습니다.");
                } else {
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        int age = cursor.getInt(2);
                        String address = cursor.getString(3);

                        textView.append("\n id = " + id + ", 이름 = " + name + ", 나이 = " + age + ", 주소 = " + address);

                        cursor.moveToNext();
                    }
                    textView.append("\n" + cursor.getCount() + " 개");
                }
                cursor.close();
            }
        });
    }
}