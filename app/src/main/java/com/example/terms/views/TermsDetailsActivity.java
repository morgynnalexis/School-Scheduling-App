package com.example.terms.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.terms.MainActivity;
import com.example.terms.R;
import com.example.terms.adapters.CoursesAdapter;
import com.example.terms.adapters.TermsAdapter;
import com.example.terms.room.table.Course;
import com.example.terms.room.table.Term;
import com.example.terms.viewmodel.TViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermsDetailsActivity extends AppCompatActivity {
    TViewModel tViewModel;
    CoursesAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton add;
    TextView name,startDate,endDate;
    int t_id;
    String _name,_s,_e;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_terms_details );
        initUi();
        t_id=getIntent().getIntExtra( "t_id" ,0);
        _name=getIntent().getStringExtra( "name" );
        _s=getIntent().getStringExtra( "s" );
        _e=getIntent().getStringExtra( "e" );
        name.setText("Term Name:- "+ _name );
        startDate.setText( "Start Date:- "+_s );
        endDate.setText("End Date:-   "+ _e );

        tViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TViewModel.class);
        tViewModel.getAllCourse().observe( this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {

                    List<Course> projectFilesList=new ArrayList<>(  );
                    for(Course projectFiles1:courses){
                        if(projectFiles1.getT_id()==t_id){
                            projectFilesList.add( projectFiles1 );
                            delete.setVisibility( View.GONE );
                        }
                    }
                    adapter.setTerms( projectFilesList );



            }
        } );


        adapter=new CoursesAdapter(this );
        recyclerView.setAdapter( adapter );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialogue();
            }
        } );

        delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Term term=new Term( _name,_s,_e );
                term.setId( t_id );
                tViewModel.delete( term );
                finish();
            }
        } );

    }

    private void initUi() {
        recyclerView=findViewById( R.id.recycler );
        add=findViewById( R.id.add );
        name=findViewById( R.id.name );
        startDate=findViewById( R.id.startDate );
        endDate=findViewById( R.id.endDate );
        delete=findViewById( R.id.delete );

    }

    private void showAddDialogue() {
        final EditText name = new EditText(TermsDetailsActivity.this);
        name.setHint( "Add Course Name" );
        final EditText startDate = new EditText(TermsDetailsActivity.this);
        startDate.setHint( "Add Course Start Date" );
        final EditText endDate = new EditText(TermsDetailsActivity.this);
        endDate.setHint( "Add Course End Date" );
        /*final EditText status = new EditText(TermsDetailsActivity.this);
        status.setHint( "Add Course Status" );*/
        final EditText mentorName = new EditText(TermsDetailsActivity.this);
        mentorName.setHint( "Add Course Mentor Name" );
        final EditText mentorPhone = new EditText(TermsDetailsActivity.this);
        mentorPhone.setHint( "Add Course Mentor Phone" );
        final EditText mentorEmail = new EditText(TermsDetailsActivity.this);
        mentorEmail.setHint( "Add Course Mentor Email" );
        final EditText note = new EditText(TermsDetailsActivity.this);
        note.setHint( "Add Course Note (Optional)" );
        ScrollView sv = new ScrollView(this);
        sv.setLayoutParams(new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout container = new LinearLayout(TermsDetailsActivity.this);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new  LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        params.rightMargin=getResources().getDimensionPixelSize( R.dimen.dialog_margin );
        name.setLayoutParams(params);
        startDate.setLayoutParams(params) ;
        endDate.setLayoutParams(params) ;

        mentorName.setLayoutParams(params) ;
        mentorEmail.setLayoutParams(params) ;
        mentorPhone.setLayoutParams(params) ;
        note.setLayoutParams(params) ;
        /////////////////////////////////////////
        final String[] selectedValue = {""};
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setLayoutParams(params) ;
        ArrayList<String> arrayList=new ArrayList<>(); //or replace your list here
        arrayList.add("In Progress");
        arrayList.add("Completed");
        arrayList.add("Dropped");
        arrayList.add("Plan to Take");

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
                      //  Toast.makeText(TermsDetailsActivity.this, selectedValue[0], Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        //////////////////////////////////////////
        container.addView( name );
        container.addView( startDate );
        container.addView( endDate );
        container.addView( radioGroup );
        container.addView( mentorName );
        container.addView( mentorPhone );
        container.addView( mentorEmail );
        container.addView( note );
        sv.addView( container );

        final CharSequence[] items = {" In Progress "," Completed "," Dropped "," Plan to Take "};
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add Course")
                .setCancelable( false )
                .setView(sv)
                .setPositiveButton("Add", null)
                .setNegativeButton( "Cancel", null )
                .show();

        Button pos=dialog.getButton( AlertDialog.BUTTON_POSITIVE );
        pos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name=name.getText().toString();
                String _start=startDate.getText().toString();
                String _end=endDate.getText().toString();
                String _status=selectedValue[0];
                String _mName=mentorName.getText().toString();
                String _mPhone=mentorPhone.getText().toString();
                String _mEmail=mentorEmail.getText().toString();
                String _note=" "+note.getText().toString();
                if(!_name.isEmpty() && !_start.isEmpty() && !_end.isEmpty() &&!_status.isEmpty()
                        && !_mName.isEmpty() && !_mPhone.isEmpty() && !_mEmail.isEmpty()
            ){
                    tViewModel.insertCourse( new Course( t_id,_name,_start,_end,_status,_mName,_mPhone,_mEmail,_note ) );
                    dialog.dismiss();
                    Toast.makeText( TermsDetailsActivity.this, "Course Added", Toast.LENGTH_SHORT ).show();

                }else {
                    Toast.makeText( TermsDetailsActivity.this, "Enter All Details", Toast.LENGTH_SHORT ).show();
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






    }

}