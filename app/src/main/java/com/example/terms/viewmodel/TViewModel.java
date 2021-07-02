package com.example.terms.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.terms.room.Repository;
import com.example.terms.room.table.Assessment;
import com.example.terms.room.table.Course;
import com.example.terms.room.table.Term;

import java.util.List;

public class TViewModel  extends AndroidViewModel {
private Repository repository;
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Assessment>> allAssessment;
    public TViewModel(@NonNull Application application) {
        super( application );
        repository=new Repository( application );
        allTerms=repository.getAllTerms();
        allCourses=repository.getAllCourse();
        allAssessment=repository.getAllAssessment();
    }
    public void insert(Term term){

        repository.insertTerm( term );
    }
    public void update(Term term){
        repository.updateTerm( term );
    }
    public void delete(Term term){
        repository.deleteTerm( term );
    }
    public LiveData<List<Term>> getAllTerms(){
        return allTerms;
    }
    ///////Assessment
    public void insertAssessment(Assessment term){

        repository.insertAssessment( term );
    }
    public void updateAssessment(Assessment term){
        repository.updateAssessment( term );
    }
    public void deleteAssessment(Assessment term){
        repository.deleteAssessment( term );
    }
    public LiveData<List<Assessment>> getAllAssessment(){
        return allAssessment;
    }
    ///////Courses
    public void insertCourse(Course term){

        repository.insertCourse( term );
    }
    public void updateCourse(Course term){
        repository.updateCourse( term );
    }
    public void deleteCourse(Course term){
        repository.deleteCourse( term );
    }
    public LiveData<List<Course>> getAllCourse(){
        return allCourses;
    }
}
