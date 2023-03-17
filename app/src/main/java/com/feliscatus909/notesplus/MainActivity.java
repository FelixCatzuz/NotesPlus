package com.feliscatus909.notesplus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.feliscatus909.notesplus.adapter.NotesListAdapter;
import com.feliscatus909.notesplus.database.RoomDB;
import com.feliscatus909.notesplus.models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    FloatingActionButton fab_add, fab_backgrounds;
    RelativeLayout relativeLayout_home;
    RecyclerView recyclerView_home;
    SearchView searchView_home;
    Notes selectedNote;
    RoomDB database;
    List<Notes> notes = new ArrayList<>();
    private NotesListAdapter notesListAdapter;

    public static boolean themes = false;
    public static boolean blue_theme = false;
    public static boolean purple_theme = false;
    public static boolean green_theme = false;
    public static boolean orange_theme = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView_home = findViewById(R.id.recyclerView_home);
        relativeLayout_home = findViewById(R.id.relativeLayout_home);
        searchView_home = findViewById(R.id.searchView_home);
        fab_add = findViewById(R.id.fab_add);
        fab_backgrounds = findViewById(R.id.fab_backgrounds);
        database = RoomDB.getInstance(this);
        notes = database.mainDao().getAll();

        updateRecycler(notes);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        fab_backgrounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu backgroundMenu = new PopupMenu(MainActivity.this, fab_backgrounds);
                backgroundMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id){
                            case R.id.menuItem_theme_blue:
                                relativeLayout_home.setBackgroundResource(R.color.blue);
                                return true;
                            case R.id.menuItem_theme_purple:
                                relativeLayout_home.setBackgroundResource(R.color.purple);
                                return true;
                            case R.id.menuItem_theme_green:
                                relativeLayout_home.setBackgroundResource(R.color.green);
                                return true;
                            case R.id.menuItem_theme_orange:
                                relativeLayout_home.setBackgroundResource(R.color.orange);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                backgroundMenu.inflate(R.menu.backgrounds_menu);
                backgroundMenu.show();
            }
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView_home.setHasFixedSize(true);
        recyclerView_home.setLayoutManager(new StaggeredGridLayoutManager(2,
                LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(notes, MainActivity.this, notesClickListener);
        recyclerView_home.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
            intent.putExtra("old_note", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopupMenu(cardView);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101){
            if (resultCode == Activity.RESULT_OK){
                Notes newNote = (Notes) data.getSerializableExtra("notes");
                database.mainDao().insert(newNote);
                notes.clear();
                notes.addAll(database.mainDao().getAll());
                notesListAdapter.notifyDataSetChanged();

            }
        }
        if (requestCode == 102){
            if (resultCode == Activity.RESULT_OK){
                Notes newNote = (Notes) data.getSerializableExtra("notes");
                database.mainDao().update(newNote.getID(), newNote.getTitle(), newNote.getNote());
                notes.clear();
                notes.addAll(database.mainDao().getAll());
                notesListAdapter.notifyDataSetChanged();

            }
        }
    }

    private void filter (String newText){
        List<Notes> filteredList = new ArrayList<>();
        for (Notes singleNote : notes){
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase()) ||
                singleNote.getNote().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(singleNote);
            }
        }
        notesListAdapter.filterList(filteredList);
    }

    private void showPopupMenu(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.pin:
                if (selectedNote.isPinned()){
                    database.mainDao().pin(selectedNote.getID(), false);
                    Toast.makeText(MainActivity.this, R.string.toast_unpinned,
                            Toast.LENGTH_SHORT).show();
                } else {
                    database.mainDao().pin(selectedNote.getID(), true);
                    Toast.makeText(MainActivity.this, R.string.toast_pinned,
                            Toast.LENGTH_SHORT).show();
                }
                notes.clear();
                notes.addAll(database.mainDao().getAll());
                notesListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.mainDao().delete(selectedNote);
                notes.remove(selectedNote);
                notesListAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, R.string.toast_del,
                        Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;
        }
    }

    public static boolean isBlue_theme() {
        return blue_theme;
    }

    public static boolean isPurple_theme() {
        return purple_theme;
    }

    public static boolean isGreen_theme() {
        return green_theme;
    }

    public static boolean isOrange_theme() {
        return orange_theme;
    }
}