package com.example.dbhw;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dbhw.Database.DB;

public class UpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);
        TextView textView = findViewById(R.id.view_log);
        Button button = findViewById(R.id.btn_search);
        TextView textView1 = findViewById(R.id.keyword_name);

        EditText editText = findViewById(R.id.edit_name);
        EditText editText1 = findViewById(R.id.edit_age);
        EditText editText2 = findViewById(R.id.edit_address);
        Button button1 = findViewById(R.id.btn_update);
        Button button2 = findViewById(R.id.back_main);
        DB db = new DB(this, "student.db", null, 1);
        SQLiteDatabase database = db.getWritableDatabase();
        Cursor cursor = db.searchData();

        if (cursor.getCount() == 0) {
            textView.append("\n 데이터가 없습니다.");
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
            textView.append("\n"+cursor.getCount() + "개");
        }
        cursor.close();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.keywordSearchData(textView1.getText().toString());
//                Cursor cursor = db.searchData();
                if (cursor.getCount() == 0) {
                    textView.append("\n 데이터가 없습니다.");
                } else {
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        int age = cursor.getInt(2);
                        String address = cursor.getString(3);

                        editText.setText(name);
                        editText1.setText(String.valueOf(age));
                        editText2.setText(address);

                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.onUpdate(
                        textView1.getText().toString(),
                        editText.getText().toString(),
                        editText1.getText().toString(),
                        editText2.getText().toString()
                );
                textView.append("\nUpdate 성공");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}