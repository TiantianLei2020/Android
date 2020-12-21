package com.tian.m3client_v1.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
public abstract class MovieDB extends RoomDatabase {
    public abstract MovieDAO movieDAO();

    private static MovieDB INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized MovieDB getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDB.class, "MovieDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
