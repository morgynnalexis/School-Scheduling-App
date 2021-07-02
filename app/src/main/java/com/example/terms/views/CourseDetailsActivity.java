package com.example.terms.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.terms.R;
import com.example.terms.adapters.AssessmentAdapter;
import com.example.terms.adapters.CoursesAdapter;
import com.example.terms.reciver.NotificationReceiver;
import com.example.terms.room.table.Assessment;
import com.example.terms.room.table.Course;
import com.example.terms.viewmodel.TViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {

    EditText name,sDate,eDate,status,mName,mPhone,mEmail,note;
    String _name,_sDate,_eDate,_status,_mName,_mPhone,_mEmail,_note;
    int c_id,t_id;
    Button update,delete,share,timer;
    FloatingActionButton add;
    TViewModel tViewModel;
    AssessmentAdapter adapter;
    RecyclerView recyclerView;
    RadioGroup radioGroup;
    RadioButton _1,_2,_3,_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_course_details );
        initUI();
        getData();
        //testNotification( 1,2,3,4,5 );
        c_id=getIntent().getIntExtra( "c_id" ,0);
        t_id=getIntent().getIntExtra( "t_id" ,0);
        _name=getIntent().getStringExtra( "name" );
        name.setText( _name );
        _sDate=getIntent().getStringExtra( "s" );
        sDate.setText( _sDate );
        _eDate=getIntent().getStringExtra( "e" );
        eDate.setText( _eDate );
        _status=getIntent().getStringExtra( "status" );
       // Toast.makeText( this, _status, Toast.LENGTH_SHORT ).show();
        switch (_status){
            case "In Progress":
                _1.setChecked( true );
                break;
            case "Completed":
                _2.setChecked( true );
                break;
            case "Dropped":
                _3.setChecked( true );
                break;
            case "Plan to Take":
                _4.setChecked( true );
                break;

        }

        _mName=getIntent().getStringExtra( "mN" );
        mName.setText( _mName );
        _mPhone=getIntent().getStringExtra( "mP" );
        mPhone.setText( _mPhone );
        _mEmail=getIntent().getStringExtra( "mE" );
        mEmail.setText( _mEmail );
        _note=getIntent().getStringExtra( "note" );
        note.setText( _note );
        timer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /**/


                showTimerDialog();
            }
        } );

        radioGroup.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton=findViewById( i );
                _status=radioButton.getText().toString();
               // Toast.makeText( CourseDetailsActivity.this, "b "+radioButton.getText(), Toast.LENGTH_SHORT ).show();

            }
        } );

        adapter=new AssessmentAdapter(this );
        recyclerView.setAdapter( adapter );
        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name=name.getText().toString();
                String _sDate=sDate.getText().toString();
                String _eDate=eDate.getText().toString();
               // String _status=status.getText().toString();
                String _mName=mName.getText().toString();
                String _mPhone=mPhone.getText().toString();
                String _mEmail=mEmail.getText().toString();
                String _note=""+note.getText().toString();
                if(!_name.isEmpty() && !_sDate.isEmpty() && !_eDate.isEmpty() && !_status.isEmpty() && !_mName.isEmpty()
                && !_mEmail.isEmpty() && !_mPhone.isEmpty()){
                    Course course=new Course( t_id,_name,_sDate,_eDate,_status,_mName,_mPhone,_mEmail,_note );
                    course.setId( c_id );
                    tViewModel.updateCourse( course );
                    Toast.makeText( CourseDetailsActivity.this, "Updated", Toast.LENGTH_SHORT ).show();
                }else {
                    Toast.makeText( CourseDetailsActivity.this, "Enter All Details", Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name=name.getText().toString();
                String _sDate=sDate.getText().toString();
                String _eDate=eDate.getText().toString();
                String _status=status.getText().toString();
                String _mName=mName.getText().toString();
                String _mPhone=mPhone.getText().toString();
                String _mEmail=mEmail.getText().toString();
                String _note=""+note.getText().toString();
                if(!_name.isEmpty() && !_sDate.isEmpty() && !_eDate.isEmpty() && !_status.isEmpty() && !_mName.isEmpty()
                        && !_mEmail.isEmpty() && !_mPhone.isEmpty()){
                    Course course=new Course( t_id,_name,_sDate,_eDate,_status,_mName,_mPhone,_mEmail,_note );
                    course.setId( c_id );
                    tViewModel.deleteCourse( course );
                    Toast.makeText( CourseDetailsActivity.this, "Deleted", Toast.LENGTH_SHORT ).show();
                    finish();

                }else {
                    //Toast.makeText( CourseDetailsActivity.this, "Enter All Details", Toast.LENGTH_SHORT ).show();
                }
                Course course=new Course( t_id,_name,_sDate,_eDate,_status,_mName,_mPhone,_mEmail,_note );
                course.setId( c_id );
                tViewModel.deleteCourse( course );
                Toast.makeText( CourseDetailsActivity.this, "Deleted", Toast.LENGTH_SHORT ).show();
                finish();
            }
        } );
        share.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sh=""+note.getText().toString();
                /*Create an ACTION_SEND Intent*/
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
               // intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, sh);
                /*Fire!*/
                startActivity(Intent.createChooser(intent, "Share Note"));
            }
        } );

        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText( CourseDetailsActivity.this, ""+projectFilesList.size(), Toast.LENGTH_SHORT ).show();
                if(projectFilesList.size()==5){
                    Toast.makeText( CourseDetailsActivity.this, "5 Assessments Added", Toast.LENGTH_SHORT ).show();

                }else {
                    showAddDialogue();
                }

            }
        } );

    }
    int mYear, mMonth, mDay, mHour=-1, mMinute;
    int nYear=0, nMonth=0, nDay=-1, nHour=0, nMinute=0;
    private void showTimerDialog() {


        Dialog dialog=new Dialog( this );
        dialog.setContentView( R.layout.layout_timer );
        dialog.getWindow().setLayout( WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        EditText txtDate=dialog.findViewById( R.id.in_date );
        EditText txtTime=dialog.findViewById( R.id.in_time );
        Button date=dialog.findViewById( R.id.btn_date );
        date.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CourseDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                nYear=year;
                                nMonth=monthOfYear + 1;
                                nDay=dayOfMonth;

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        } );
        Button time=dialog.findViewById( R.id.btn_time );
        time.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(CourseDetailsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                                nHour=hourOfDay;
                                nMinute=minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        } );
        Button add=dialog.findViewById( R.id.add );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nDay!=-1 && nHour!=-1){
                    addNotification(nYear,nMonth,nDay,nHour,nMinute);

                    dialog.dismiss();
                }else {
                    Toast.makeText( CourseDetailsActivity.this, "Select Date and Time", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        dialog.show();
    }

    private void testNotification(int nYear, int nMonth, int nDay, int nHour, int nMinute) {
        Intent intent1 = new Intent( CourseDetailsActivity.this, NotificationReceiver.class );
        intent1.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent1.putExtra( "title", "Test Notification");
        PendingIntent pendingIntent = PendingIntent.getBroadcast( CourseDetailsActivity.this, 99789, intent1, PendingIntent.FLAG_UPDATE_CURRENT );
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService( Context.ALARM_SERVICE );
        Calendar calendar = Calendar.getInstance();
        //  Calendar cal = Calendar.getInstance();
        /*cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();
        cal.set(nYear,nMonth,nDay,nHour,nMinute);*/
        //  calendar.setTimeInMillis( System.currentTimeMillis() );
        calendar.set( Calendar.YEAR,nYear );
        calendar.set( Calendar.MONTH,nMonth );
        calendar.set( Calendar.DAY_OF_MONTH,nDay );
        calendar.set( Calendar.HOUR_OF_DAY, nHour );
        calendar.set( Calendar.MINUTE, nMinute );
        Toast.makeText( CourseDetailsActivity.this, "Test", Toast.LENGTH_SHORT ).show();
        long timesec=System.currentTimeMillis();
        long ten=1000*10;
        assert am != null;
        am.set( AlarmManager.RTC_WAKEUP,ten,pendingIntent );
    }
    private void addNotification(int nYear, int nMonth, int nDay, int nHour, int nMinute) {
        Intent intent1 = new Intent( CourseDetailsActivity.this, NotificationReceiver.class );
        intent1.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent1.putExtra( "title", _mName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast( CourseDetailsActivity.this, 322, intent1, PendingIntent.FLAG_UPDATE_CURRENT );
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService( Context.ALARM_SERVICE );
        Calendar calendar = Calendar.getInstance();
        //calendar.set( Calendar.YEAR,nYear );
        Log.e( "aralrf"," "+nYear );
      //  calendar.set( Calendar.MONTH,nMonth );
        Log.e( "aralrf"," "+nMonth );
       // calendar.set( Calendar.DAY_OF_MONTH,nDay );
        Log.e( "aralrf"," "+nDay );
        calendar.set( Calendar.HOUR_OF_DAY, nHour );
        Log.e( "aralrf"," "+nHour );
        calendar.set( Calendar.MINUTE, nMinute );
        Log.e( "aralrf"," "+nMinute );
        Log.e( "aralrf","milli "+calendar.getTimeInMillis() );
        Toast.makeText( CourseDetailsActivity.this, "Alarm Set", Toast.LENGTH_SHORT ).show();
        assert am != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setAndAllowWhileIdle( AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent );
        }
    }

    private void showAddDialogue() {
        final EditText name = new EditText(CourseDetailsActivity.this);
        name.setHint( "Add Assessment Title" );
        final EditText startDate = new EditText(CourseDetailsActivity.this);
        startDate.setHint( "Add Expected Completion Date" );

        final String[] selectedValue = {""};
        RadioGroup radioGroup = new RadioGroup(this);

        ArrayList<String> arrayList=new ArrayList<>(); //or replace your list here
        arrayList.add("Objective");
        arrayList.add("Performance");


        for (int i = 0; i < arrayList.size(); i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(arrayList.get(i));
            radioGroup.addView(radioButton);

            int keyI = i;
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (radioButton.isChecked()){
                        selectedValue[0] = arrayList.get(keyI);
                        //Toast.makeText(CourseDetailsActivity.this, selectedValue[0], Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        /////////////////////////////////////////////////////////////
        ScrollView scrollView=new ScrollView( this );
        LinearLayout container = new LinearLayout(CourseDetailsActivity.this);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new  LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        params.rightMargin=getResources().getDimensionPixelSize( R.dimen.dialog_margin );
        name.setLayoutParams(params);
        startDate.setLayoutParams(params) ;
        //endDate.setLayoutParams(params) ;
        radioGroup.setLayoutParams(params) ;
        note.setLayoutParams(params) ;
        container.addView( name );
        container.addView( startDate );
      //  container.addView( endDate );
        container.addView(radioGroup );
        scrollView.addView( container );
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add Assessment")
                .setCancelable( false )
                .setView(scrollView)
                .setPositiveButton("Add", null)
                .setNegativeButton( "Cancel", null )
                .show();
        Button pos=dialog.getButton( AlertDialog.BUTTON_POSITIVE );
        pos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name=name.getText().toString();
                String _start=startDate.getText().toString();
               // String _end=endDate.getText().toString();
                String _performance=selectedValue[0];

                if(!_name.isEmpty() && !_start.isEmpty()  &&!_performance.isEmpty()
                ){
                    //counter++;
                    tViewModel.insertAssessment( new Assessment( c_id,_name,_start,"",_performance,"" ) );
                    dialog.dismiss();
                    Toast.makeText( CourseDetailsActivity.this, "Assessment Added", Toast.LENGTH_SHORT ).show();

                }else {
                    Toast.makeText( CourseDetailsActivity.this, "Enter All Details", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        Button delete=dialog.getButton( AlertDialog.BUTTON_NEGATIVE );
        delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        } );



        /**/

        //getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);


    }

    List<Assessment> projectFilesList;
    private void getData() {
        tViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get( TViewModel.class);
        tViewModel.getAllAssessment().observe( this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> courses) {
                 projectFilesList=new ArrayList<>(  );
                for(Assessment projectFiles1:courses){
                    if(projectFiles1.getC_id()==c_id){
                        projectFilesList.add( projectFiles1 );
                    }
                }
                adapter.setTerms( projectFilesList );
               // Toast.makeText( TermsDetailsActivity.this, "aaaaaaaa" + projectFilesList.size(), Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    private void initUI() {
        name=findViewById( R.id.name );
        sDate=findViewById( R.id.sDate );
        eDate=findViewById( R.id.eDate );
        status=findViewById( R.id.status );
        mName=findViewById( R.id.mName );
        add=findViewById( R.id.add );
        recyclerView=findViewById( R.id.recycler );
        mPhone=findViewById( R.id.mPhone );
        mEmail=findViewById( R.id.mEmail );
        note=findViewById( R.id.note );
        delete=findViewById( R.id.delete );
        update=findViewById( R.id.update );
        share=findViewById( R.id.share );
        timer=findViewById( R.id.timer );
        radioGroup=findViewById( R.id.group );
        _1=findViewById( R.id._1 );
        _2=findViewById( R.id._2 );
        _3=findViewById( R.id._3 );
        _4=findViewById( R.id._4 );
    }
}