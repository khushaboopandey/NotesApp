package com.chandan.notelistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;

import static com.chandan.notelistapp.MainActivity.notes;
import static com.chandan.notelistapp.MainActivity.set;

public class EditNote extends AppCompatActivity implements TextWatcher {
    private FloatingActionButton fab2;
    EditText Multitext;
    int noteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note2);
        fab2 = findViewById(R.id.fab22);
        Multitext = findViewById(R.id.multieditText);

        Intent i = getIntent();
        i.getIntExtra("noteId",-1);

        if (noteId !=1 ){
            String filterText = notes.get(noteId);
            Multitext.setText(filterText);
        }

        Multitext.addTextChangedListener(this);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"testing",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditNote.this,MainActivity.class);
                 startActivity(intent);

            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        notes.set(noteId,String.valueOf(s));
        MainActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.chandan.notelistapp", Context.MODE_PRIVATE);

        if (set != null){
            set = new HashSet<String>();

        }else {
            set.clear();
        }
        set.clear();
        set.addAll(notes);
        sharedPreferences.edit().remove("notes").apply();
        sharedPreferences.edit().putStringSet("notes",set).apply();


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
