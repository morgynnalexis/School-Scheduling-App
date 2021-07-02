package com.example.terms.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.terms.room.table.Assessment;
import com.example.terms.room.table.Course;
import com.example.terms.room.table.Term;

@androidx.room.Database(entities = {Term.class, Course.class, Assessment.class}, version = 2)
public abstract class Database extends RoomDatabase {
    private static Database instance;
    public abstract Dao dao();
    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder( context.getApplicationContext(),
                    Database.class, "database" )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
   /* public static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate( db );

        }
    };

    private static class PopulateDb extends AsyncTask<Void,Void,Void> {
        private ProjectDao projectDao;
        private PopulateDb(ProjectDatabase projectDatabase){
            projectDao=projectDatabase.projectDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            projectDao.insert( new Project(  "Name") );
            return null;
        }
    }*/


}
