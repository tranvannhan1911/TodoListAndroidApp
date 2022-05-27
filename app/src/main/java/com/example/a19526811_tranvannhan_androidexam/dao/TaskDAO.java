package com.example.a19526811_tranvannhan_androidexam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a19526811_tranvannhan_androidexam.entity.Task;

@Dao
public abstract class TaskDAO {
    @Insert
    public abstract void insert(Task task);

    @Query("SELECT * FROM tasks WHERE id = :id")
    public abstract Task findById(String id);

    public boolean checkExits(String id){
        return findById(id) != null;
    }
}
