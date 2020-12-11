package com.example.mt_lr3.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mt_lr3.Objects.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id=:id")
    List<User> getCurrent(Integer id);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
