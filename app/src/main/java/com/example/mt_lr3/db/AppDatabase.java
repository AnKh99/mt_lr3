package com.example.mt_lr3.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mt_lr3.Objects.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
