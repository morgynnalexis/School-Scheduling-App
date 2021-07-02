package com.example.terms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.terms.adapters.TermsAdapter;
import com.example.terms.room.Repository;
import com.example.terms.room.table.Term;
import com.example.terms.viewmodel.TViewModel;
import com.example.terms.views.TermsDetailsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TViewModel tViewModel;
     List<Term> termList;
    TermsAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initUi();
        createNotification();
        tViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TViewModel.class);
        tViewModel.getAllTerms().observe( this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {

                    termList=terms;
                    adapter.setTerms( terms );



            }
        } );


        adapter=new TermsAdapter(this );
        recyclerView.setAdapter( adapter );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialogue();
            }
        } );

    }
    private void createNotification(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int imp= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel( "1" ,"chanel",imp);
            channel.setDescription( "" );
            //channel.setVibrationPattern(new long[]{ 0 });
            // channel.enableVibration(true);
            channel.setLockscreenVisibility( NotificationCompat.PRIORITY_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
           /* Uri sound = Uri.parse( ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +getPackageName() + "/" + R.raw.azan);
            channel.setLightColor( Color.YELLOW);
            channel.enableLights(true);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound( sound,attributes );*/
            NotificationManager notificationManager=getSystemService( NotificationManager.class );
            assert notificationManager != null;
            notificationManager.createNotificationChannel( channel );
        }

    }

    private void initUi() {
        recyclerView=findViewById( R.id.recycler );
        add=findViewById( R.id.add );

    }

    private void showAddDialogue() {
        final EditText name = new EditText(MainActivity.this);
        name.setHint( "Add Term Name" );
        final EditText startDate = new EditText(MainActivity.this);
        startDate.setHint( "Add Term Start Date" );
        final EditText endDate = new EditText(MainActivity.this);
        endDate.setHint( "Add Term End Date" );
        LinearLayout container = new LinearLayout(MainActivity.this);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new  LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        params.rightMargin=getResources().getDimensionPixelSize( R.dimen.dialog_margin );
        name.setLayoutParams(params);
        startDate.setLayoutParams(params) ;
        endDate.setLayoutParams(params) ;
        container.addView( name );
        container.addView( startDate );
        container.addView( endDate );
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add Term")
                .setCancelable( false )
                .setView(container)
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
                if(!_name.isEmpty() && !_start.isEmpty() && !_end.isEmpty()){
                    tViewModel.insert( new Term( _name,_start,_end ) );
                    dialog.dismiss();
                    Toast.makeText( MainActivity.this, "Term Added", Toast.LENGTH_SHORT ).show();
                }else {
                    Toast.makeText( MainActivity.this, "Enter All Details", Toast.LENGTH_SHORT ).show();
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