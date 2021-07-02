package com.example.terms.room;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.terms.room.table.Assessment;
import com.example.terms.room.table.Course;
import com.example.terms.room.table.Term;

import java.util.List;

public class Repository {
    private Dao dao;
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Course>> allCourse;
    private LiveData<List<Assessment>> allAssessment;
    public Repository (Application application){
        Database database=Database.getInstance( application );
        dao=database.dao();
        allTerms =dao.getAll();
        allCourse=dao.getAllCourse();
        allAssessment=dao.getAllAssessment();

    }
    public LiveData<List<Term>> getAllTerms(){
        return allTerms;
    }
    public void insertTerm(final Term term){
        new InsertTerm( dao ).execute( term );
    }
    private static class InsertTerm extends AsyncTask<Term,Void,Void> {
        Dao projectDao;
        private InsertTerm(Dao projectDao){
            this.projectDao=projectDao;
        }
        @Override
        protected Void doInBackground(Term... projects) {
            projectDao.insert( projects[0] );
            return null;
        }
    }
    public void updateTerm(Term term){
        dao.update( term );

    }
    public void deleteTerm(Term term){
        new DeleteTerm( dao ).execute( term );

    }
    private static class DeleteTerm extends AsyncTask<Term,Void,Void> {
        Dao projectDao;
        private DeleteTerm(Dao projectDao){
            this.projectDao=projectDao;
        }
        @Override
        protected Void doInBackground(Term... projects) {
            projectDao.delete( projects[0] );
            return null;
        }
    }



    //Courses

    public LiveData<List<Course>> getAllCourse(){
        return allCourse;
    }
    public void insertCourse(final Course term){
        new InsertCourse( dao ).execute( term );
    }
    private static class InsertCourse extends AsyncTask<Course,Void,Void> {
        Dao projectDao;
        private InsertCourse(Dao projectDao){
            this.projectDao=projectDao;
        }
        @Override
        protected Void doInBackground(Course... projects) {
            projectDao.insertCourse( projects[0] );
            return null;
        }
    }
    public void updateCourse(Course term){
        new UpdateCourse( dao ).execute( term );

    }
    private static class UpdateCourse extends AsyncTask<Course,Void,Void> {
        Dao projectDao;
        private UpdateCourse(Dao projectDao){
            this.projectDao=projectDao;
        }
        @Override
        protected Void doInBackground(Course... projects) {
            projectDao.updateCourse( projects[0] );
            return null;
        }
    }
    public void deleteCourse(Course term){
        new DeleteCourse( dao ).execute( term );

    }
    private static class DeleteCourse extends AsyncTask<Course,Void,Void> {
        Dao projectDao;
        private DeleteCourse(Dao projectDao){
            this.projectDao=projectDao;
        }
        @Override
        protected Void doInBackground(Course... projects) {
            projectDao.deleteCourse( projects[0] );
            return null;
        }
    }



    //Assessment

    public LiveData<List<Assessment>> getAllAssessment(){
        return allAssessment;
    }
    public void insertAssessment(final Assessment term){
        new InsertAssessment( dao ).execute( term );
    }
    private static class InsertAssessment extends AsyncTask<Assessment,Void,Void> {
        Dao projectDao;
        private InsertAssessment(Dao projectDao){
            this.projectDao=projectDao;
        }
        @Override
        protected Void doInBackground(Assessment... projects) {
            projectDao.insertAssessment( projects[0] );
            return null;
        }
    }
    public void updateAssessment(Assessment term){
        new UpdateAssessment( dao ).execute( term );

    }
    private static class UpdateAssessment extends AsyncTask<Assessment,Void,Void> {
        Dao projectDao;
        private UpdateAssessment(Dao projectDao){
            this.projectDao=projectDao;
        }
        @Override
        protected Void doInBackground(Assessment... projects) {
            projectDao.updateAssessment( projects[0] );
            return null;
        }
    }
    public void deleteAssessment(Assessment term){
        new DeleteAssessment( dao ).execute( term );

    }
    private static class DeleteAssessment extends AsyncTask<Assessment,Void,Void> {
        Dao projectDao;
        private DeleteAssessment(Dao projectDao){
            this.projectDao=projectDao;
        }
        @Override
        protected Void doInBackground(Assessment... projects) {
            projectDao.deleteAssessment( projects[0] );
            return null;
        }
    }

}
