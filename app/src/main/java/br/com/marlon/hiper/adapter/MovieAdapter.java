package br.com.marlon.hiper.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import br.com.marlon.hiper.R;
import br.com.marlon.hiper.model.Filme;
import br.com.marlon.hiper.util.Constants;

/**
 * Created by Marlon on 28/10/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.PersonViewHolder> {

    private ArrayList<Filme> filmes;
    private Context context;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {


        TextView title, count;
        ImageView thumbnail;


        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

            title = itemView.findViewById(R.id.title);
            count = itemView.findViewById(R.id.count);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }

    public MovieAdapter(Context context, ArrayList<Filme> filmes) {
        this.context = context;
        this.filmes = filmes;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Filme filme = filmes.get(position);
        holder.title.setText(filme.getTitle());
        holder.count.setText(filme.getRelease_date().substring(0,4));
        if(filme.getRelease_date().substring(0,4).equals("2017")){
            holder.count.setTextColor(context.getResources().getColor(R.color.red));
        }else{
            holder.count.setTextColor(context.getResources().getColor(R.color.black));
        }


        // loading album cover using Glide library
        Glide.with(context)
                .load(Constants.URLIMAGEM + filme.getPoster_path())
                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }
}
