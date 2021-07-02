package com.example.terms.room;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.terms.room.table.Assessment;
import com.example.terms.room.table.Course;
import com.example.terms.room.table.Term;

import java.util.List;
@androidx.room.Dao
public interface Dao {
    @Insert
    void insert(Term term);
    @Update
    void update(Term term);
    @Delete
    void delete(Term term);
    @Query( "Select * from term" )
    LiveData<List<Term>> getAll();
    //Courses
    @Insert
    void insertCourse(Course course);
    @Update
    void updateCourse(Course term);
    @Delete
    void deleteCourse(Course term);
    @Query( "Select * from course" )
    LiveData<List<Course>> getAllCourse();
    //Assessment
    @Insert
    void insertAssessment(Assessment assessment);
    @Update
    void updateAssessment(Assessment assessment);
    @Delete
    void deleteAssessment(Assessment assessment);
    @Query( "Select * from assessment" )
    LiveData<List<Assessment>> getAllAssessment();

}
