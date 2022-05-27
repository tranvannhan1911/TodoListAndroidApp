package com.example.a19526811_tranvannhan_androidexam.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.a19526811_tranvannhan_androidexam.dao.TaskDAO;
import com.example.a19526811_tranvannhan_androidexam.entity.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class SqlDatabase extends RoomDatabase {
    public abstract TaskDAO getTaskDAO();
}
