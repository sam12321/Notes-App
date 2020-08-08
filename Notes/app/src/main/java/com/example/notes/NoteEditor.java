package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Locale;

public class NoteEditor extends AppCompatActivity {

    EditText editText;
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editText = (EditText) findViewById(R.id.editText2);

        Intent intent = getIntent();
        n = intent.getIntExtra("item", -1);

        if (n !=-1 ) {

            editText.setText("Sample Note");

        } else {
            editText.setText("");
            editText.setHint("Enter Text");
        }


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MainActivity.notesList.set(n,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set=new HashSet<>(MainActivity.notesList);
                sharedPreferences.edit().putStringSet("set",set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
}
