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
import com.example.terms.room.table.Term;
import com.example.terms.views.TermsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.Views> {

    List<Term> terms=new ArrayList<>();
    Context context;
    public TermsAdapter (Context context){
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
        Term term=terms.get( position );
        holder.name.setText( term.getTermName() );
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, TermsDetailsActivity.class );
                intent.putExtra( "t_id",term.getId() );
                intent.putExtra( "name",term.getTermName() );
                intent.putExtra( "s",term.getStartDate() );
                intent.putExtra( "e",term.getEndDate() );
                context.startActivity( intent );
            }
        } );

    }

    public void setTerms(List<Term> terms){
        this.terms=terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(terms!=null){
            return terms.size();
        }else {
            return 0;
        }

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
