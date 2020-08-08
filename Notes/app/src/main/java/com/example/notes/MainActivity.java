package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Intent intent;
    static ArrayList<String> notesList;
    static ArrayAdapter<String> arrayAdapter;

    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.addnote:
            //    Toast.makeText(getApplicationContext(), "Working on it bro!!", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), NoteEditor.class);
                intent.putExtra("item",notesList.size());
                notesList.add("new note");
                arrayAdapter.notifyDataSetChanged();
                Log.i("hhh", String.valueOf(notesList.size()));
                startActivity(intent);
                //return  true;
            default:
                System.out.println("haha");
                // return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);

        listView = (ListView) findViewById(R.id.listView);

        notesList = new ArrayList<>();


        HashSet<String> set=(HashSet<String>)sharedPreferences.getStringSet("set",null);

        if(set==null) {
            notesList.add("Sample Note...");
        }
        else{
            notesList=new ArrayList<>(set);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notesList);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent = new Intent(getApplicationContext(), NoteEditor.class);
                intent.putExtra("item", position);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Are you sure")
                                .setMessage("The selected note will be permenantly deleted!")
                                .setNegativeButton("No",null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        notesList.remove(position);
                                        arrayAdapter.notifyDataSetChanged();



                                        HashSet<String> set=new HashSet<>(MainActivity.notesList);
                                        sharedPreferences.edit().putStringSet("set",set).apply();

                                    }
                                })
                                .show();



                return true;
            }
        });
    }
}
