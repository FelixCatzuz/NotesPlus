package com.feliscatus909.notesplus.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.feliscatus909.notesplus.models.Notes;

@Database(entities = Notes.class, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDatabase roomDatabase;
    private static final String DATABASE_NAME = "NotesPlusApp";

    public static synchronized RoomDB getInstance(Context context){
        if (roomDatabase == null){
            roomDatabase = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class,
                    DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return (RoomDB) roomDatabase;
    }

    public abstract MainDao mainDao();
}
