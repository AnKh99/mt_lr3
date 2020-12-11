package com.example.mt_lr3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.example.mt_lr3.db.AppDatabase;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "users")
                .allowMainThreadQueries()
                .build();
        if(database.isOpen()){
            Log.d("DB", "OK");
        }
    }

    public static MainActivity getInstance(){
        return instance;
    }
    public AppDatabase getDatabase(){
        return database;
    }
}