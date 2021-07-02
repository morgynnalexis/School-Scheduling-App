package com.example.terms.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.terms.R;
import com.example.terms.room.table.Course;
import com.example.terms.room.table.Term;
import com.example.terms.views.CourseDetailsActivity;
import com.example.terms.views.TermsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.Views> {

    List<Course> terms=new ArrayList<>();
    Context context;
    public CoursesAdapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public Views onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_card ,parent,false) ;
        return new Views( view );
    }

    @Override
    public void onBindViewHolder(@NonNull Views holder, int position) {
        Course term=terms.get( position );
        holder.name.setText( term.getName() );
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CourseDetailsActivity.class );
                intent.putExtra( "c_id",term.getId() );
                intent.putExtra( "t_id",term.getT_id() );
                intent.putExtra( "name",term.getName() );
                intent.putExtra( "s",term.getStartDate() );
                intent.putExtra( "e",term.getEndDate() );
                intent.putExtra( "status",term.getStatus() );
                intent.putExtra( "mN",term.getMentorName() );
                intent.putExtra( "mP",term.getMentorPhone() );
                intent.putExtra( "mE",term.getMentorEmail() );
                intent.putExtra( "note",term.getNote() );
                context.startActivity( intent );
            }
        } );

    }

    public void setTerms(List<Course> terms){
        this.terms=terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public class Views extends RecyclerView.ViewHolder {
        TextView name;
        ImageView edit,delete;
        public Views(@NonNull View itemView) {
            super( itemView );
            name=itemView.findViewById( R.id.name );
        }
    }
}
