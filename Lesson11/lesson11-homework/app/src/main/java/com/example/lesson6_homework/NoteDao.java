package com.example.lesson6_homework;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Query("SELECT * FROM note WHERE noteIndex IN (:userIds)")
    List<Note> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(Note... notes);

    @Query("SELECT * FROM note WHERE noteIndex = :noteIndex")
    Note getById(int noteIndex);

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);


}
