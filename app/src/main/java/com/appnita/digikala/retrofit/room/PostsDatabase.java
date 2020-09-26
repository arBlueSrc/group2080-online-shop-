package com.appnita.digikala.retrofit.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.appnita.digikala.ui.RecyclerObjectClass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {RecyclerObjectClass.class},version = 1,exportSchema = false)
public abstract class PostsDatabase extends RoomDatabase {

    public abstract PostsDao postsDao();
    private static volatile PostsDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PostsDatabase getDatabase (final Context context){
        if(INSTANCE == null){
            synchronized (PostsDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PostsDatabase.class,"posts_database").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
