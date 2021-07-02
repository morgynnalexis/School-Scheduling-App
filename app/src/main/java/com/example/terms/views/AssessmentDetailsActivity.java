package com.example.terms.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.terms.R;
import com.example.terms.reciver.NotificationReceiver;
import com.example.terms.room.table.Assessment;
import com.example.terms.room.table.Course;
import com.example.terms.viewmodel.TViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AssessmentDetailsActivity extends AppCompatActivity {
    EditText name,sDate,eDate,pref,obj;
    String _name,_sDate,_eDate,_pref,_obj;
    int a_id,c_id;
    Button update,delete,timer;
    TViewModel tViewModel;
    RadioGroup radioGroup;
    RadioButton _1,_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_assessment_details );
        initUI();
        getData();
        a_id=getIntent().getIntExtra( "a_id" ,0);
        c_id=getIntent().getIntExtra( "c_id" ,0);
        _name=getIntent().getStringExtra( "name" );
        name.setText( _name );
        _sDate=getIntent().getStringExtra( "s" );
        sDate.setText( _sDate );
        _eDate=getIntent().getStringExtra( "e" );
        eDate.setText( _eDate );
        _pref=getIntent().getStringExtra( "perf" );
        //Toast.makeText( this, _pref, Toast.LENGTH_SHORT ).show();
        switch (_pref){
            case "Objective":
                _2.setChecked( true );
                break;
            case "Performance":
                _1.setChecked( true );
                break;
        }
       //pref.setText( _pref );
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
                _pref=radioButton.getText().toString();
                //Toast.makeText( AssessmentDetailsActivity.this, _pref, Toast.LENGTH_SHORT ).show();
            }
        } );

        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name=name.getText().toString();
                String _sDate=sDate.getText().toString();
                String _eDate=eDate.getText().toString();
               // String _pref=pref.getText().toString();
               // String _obj=obj.getText().toString();
                if(!_name.isEmpty() && !_sDate.isEmpty()  && !_pref.isEmpty()
                        ){
                    Assessment course=new Assessment( c_id,_name,_sDate,"",_pref,"" );
                    course.setId( a_id );
                    tViewModel.updateAssessment( course );
                    Toast.makeText( AssessmentDetailsActivity.this, "Updated", Toast.LENGTH_SHORT ).show();
                }else {
                    Toast.makeText( AssessmentDetailsActivity.this, "Enter All Details", Toast.LENGTH_SHORT ).show();
                }

            }
        } );

        delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name=name.getText().toString();
                String _sDate=sDate.getText().toString();
                String _eDate=eDate.getText().toString();
              //  String _pref=pref.getText().toString();

                if(!_name.isEmpty() && !_sDate.isEmpty() && !_eDate.isEmpty() && !_pref.isEmpty()
                ){

                }else {
                   // Toast.makeText( AssessmentDetailsActivity.this, "Enter All Details", Toast.LENGTH_SHORT ).show();
                }
                Assessment course=new Assessment( c_id,_name,_sDate,_eDate,_pref,"" );
                course.setId( a_id );
                tViewModel.deleteAssessment( course );
                Toast.makeText( AssessmentDetailsActivity.this, "Deleted", Toast.LENGTH_SHORT ).show();
                finish();
            }
        } );

    }

    private void getData() {
        tViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get( TViewModel.class);
        tViewModel.getAllAssessment().observe( this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> courses) {
              /*  List<Assessment> projectFilesList=new ArrayList<>(  );
                for(Assessment projectFiles1:courses){
                    if(projectFiles1.getC_id()==c_id){
                        projectFilesList.add( projectFiles1 );
                    }
                }
                adapter.setTerms( projectFilesList );*/
                // Toast.makeText( TermsDetailsActivity.this, "aaaaaaaa" + projectFilesList.size(), Toast.LENGTH_SHORT ).show();
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(AssessmentDetailsActivity.this,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(AssessmentDetailsActivity.this,
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(nDay!=-1 && nHour!=-1){
                    addNotification(nYear,nMonth,nDay,nHour,nMinute);

                    dialog.dismiss();
                }else {
                    Toast.makeText( AssessmentDetailsActivity.this, "Select Date and Time", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        dialog.show();
    }


    private void addNotification(int nYear, int nMonth, int nDay, int nHour, int nMinute) {
        Intent intent1 = new Intent( AssessmentDetailsActivity.this, NotificationReceiver.class );
        intent1.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent1.putExtra( "title", _name);
        PendingIntent pendingIntent = PendingIntent.getBroadcast( AssessmentDetailsActivity.this, 99789, intent1, PendingIntent.FLAG_UPDATE_CURRENT );
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService( Context.ALARM_SERVICE );
        Calendar calendar = Calendar.getInstance();
        //  Calendar cal = Calendar.getInstance();
        /*cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();
        cal.set(nYear,nMonth,nDay,nHour,nMinute);*/
        //  calendar.setTimeInMillis( System.currentTimeMillis() );
        //calendar.set( Calendar.YEAR,nYear );
        //calendar.set( Calendar.MONTH,nMonth );
        //calendar.set( Calendar.DAY_OF_MONTH,nDay );
        calendar.set( Calendar.HOUR_OF_DAY, nHour );
        calendar.set( Calendar.MINUTE, nMinute );
        Toast.makeText( AssessmentDetailsActivity.this, "Alarm Set", Toast.LENGTH_SHORT ).show();
        long timesec=System.currentTimeMillis();
        long ten=1000*10;
        assert am != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setAndAllowWhileIdle( AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent );
        }
    }



    private void initUI() {
        name=findViewById( R.id.name );
        sDate=findViewById( R.id.sDate );
        eDate=findViewById( R.id.eDate );
        pref=findViewById( R.id.perf );
        obj=findViewById( R.id.obj );
        radioGroup=findViewById( R.id.group );
        _1=findViewById( R.id._1 );
        _2=findViewById( R.id._2 );
        delete=findViewById( R.id.delete );
        update=findViewById( R.id.update );
        timer=findViewById( R.id.timer );
    }
}