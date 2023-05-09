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

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        TextView textView = findViewById(R.id.query);
        Button button = findViewById(R.id.delete);
        EditText editText = findViewById(R.id.keyword_name);
        TextView textView2 = findViewById(R.id.view_age);
        TextView textView3 = findViewById(R.id.view_address);
        Button button1 = findViewById(R.id.back_main);
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

                db.onDelete(editText.getText().toString());
                Cursor cursor = db.keywordSearchData(editText.getText().toString());
//                Cursor cursor = db.searchData();
                if (cursor.getCount() == 0) {
                    textView.append("\n delete 실패 - 항목이 없습니다."+cursor.getCount()+"번째 row delete 성공");
                } else {
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        int age = cursor.getInt(2);
                        String address = cursor.getString(3);

                        textView2.setText(String.valueOf(age));
                        textView3.setText(address);
                        textView.append("\n"+cursor.getCount()+"번째 row delete 성공");
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}