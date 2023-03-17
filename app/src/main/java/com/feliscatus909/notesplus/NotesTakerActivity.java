package com.feliscatus909.notesplus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.feliscatus909.notesplus.models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {

    ImageView imageView_save;
    EditText editText_title;
    EditText editText_note;
    Notes notes;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        imageView_save = findViewById(R.id.imageView_save);
        editText_title = findViewById(R.id.editText_title);
        editText_note = findViewById(R.id.editText_note);

        notes = new Notes();

        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_title.setText(notes.getTitle());
            editText_note.setText(notes.getNote());
            isOldNote = true;
        } catch (Exception e){
            e.printStackTrace();
        }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String description = editText_note.getText().toString();

                if (description.isEmpty()){
                    Toast.makeText(NotesTakerActivity.this,
                            R.string.toast_empty_description,
                            Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
                Date date  = new Date();

                if (!isOldNote){
                    notes = new Notes();
                }

                notes.setTitle(title);
                notes.setNote(description);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("notes", notes);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}