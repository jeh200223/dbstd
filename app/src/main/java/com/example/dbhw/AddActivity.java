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

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Button button = findViewById(R.id.btn_insert);
        EditText editText = findViewById(R.id.insert_name);
        EditText editText1 = findViewById(R.id.insert_age);
        EditText editText2 = findViewById(R.id.insert_address);
        TextView textView3 = findViewById(R.id.view_log);
        Button button1 = findViewById(R.id.btn_back_main);
        DB db = new DB(this, "student.db", null, 1);
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        db.onCreate(sqLiteDatabase);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = db.insertData(editText.getText().toString(), Integer.parseInt(editText1.getText().toString()), editText2.getText().toString());
                if(result == -1L) {
                    textView3.append("\n중복된 데이터 입니다.");
                } else {
                    Cursor cursor = db.searchData();
                    textView3.append("\n".concat(String.valueOf(cursor.getCount())).concat("번째 row insert 성공"));
                }
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