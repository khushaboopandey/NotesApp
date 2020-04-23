package com.chandan.notelistapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static ArrayAdapter arrayAdapter;
    static ArrayList<String> notes = new ArrayList();
    private FloatingActionButton fab;
    ListView List;
    static Set<String> set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab2);
        List = findViewById(R.id.list);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.chandan.notelistapp", Context.MODE_PRIVATE);
//        sharedPreferences.edit().putString("name","khus").apply();
//        String name = sharedPreferences.getString("name","");
//        Log.i("Name",name);
        set = sharedPreferences.getStringSet("notes",null);
        notes.clear();

        if (set != null){
            notes.addAll(set);
        }else {
             notes.add("Example note");
            set = new HashSet<String>();
            set.addAll(notes);
            sharedPreferences.edit().remove("notes").apply();
            sharedPreferences.edit().putStringSet("notes",set).apply();
        }


      //  notes.add("Examples Notes");
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        List.setAdapter(arrayAdapter);

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditNote.class);
                intent.putExtra("noteId",position);
                startActivity(intent);

            }
        });

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are You Sure?")
                        .setMessage("Do You Want to delete this Item ? ")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              notes.remove(position);
                                SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("com.chandan.notelistapp", Context.MODE_PRIVATE);

                                if (set != null){
                                    set = new HashSet<String>();

                                }else {
                                    set.clear();
                                }
                                set.addAll(MainActivity.notes);
                                sharedPreferences.edit().remove("notes").apply();
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                         .show();
             }
        });



      fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.add("");

                SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("com.chandan.notelistapp", Context.MODE_PRIVATE);

                if (set != null){
                    set = new HashSet<String>();

                }else {
                    set.clear();
                }
                set.addAll(notes);
                sharedPreferences.edit().remove("notes").apply();
                sharedPreferences.edit().putStringSet("notes",set).apply();
                arrayAdapter.notifyDataSetChanged();

                Intent intent = new Intent(MainActivity.this,EditNote.class);
                intent.putExtra("noteId",notes.size()-1);
                startActivity(intent);

            }
        });



    }
}
