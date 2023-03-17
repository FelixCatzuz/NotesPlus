package com.feliscatus909.notesplus.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.feliscatus909.notesplus.models.Notes;

import java.util.List;

@SuppressWarnings("ALL")
@Dao
public interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Notes notes);

    @Query("SELECT * FROM notes ORDER BY ID")
    List<Notes> getAll();

    @Query("UPDATE notes SET title =:title, note =:note WHERE ID =:id")
    void update(int id, String title, String note);

    @Delete
    void delete(Notes notes);

    @Query("UPDATE notes SET pinned =:pinned WHERE ID =:id")
    void pin(int id, boolean pinned);
}
