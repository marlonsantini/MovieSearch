package br.com.marlon.hiper.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import br.com.marlon.hiper.R;
import br.com.marlon.hiper.facade.Facade;
import br.com.marlon.hiper.model.Creditos;
import br.com.marlon.hiper.model.Filme;
import br.com.marlon.hiper.util.Constants;
import br.com.marlon.hiper.util.TicketView;

public class MovieInternaActivity extends AppCompatActivity {

    private Facade facade;
    private int idFilme;


    private TextView txtName, txtDuration, txtDirector, txtGenre, txtRating, txtPrice, txtError;
    private ImageView imgPoster;
    private ProgressBar progressBar;
    private TicketView ticketView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_interna);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        txtName = findViewById(R.id.name);
        txtDirector = findViewById(R.id.director);
        txtDuration = findViewById(R.id.duration);
        imgPoster = findViewById(R.id.poster);
        imgPoster = findViewById(R.id.poster);
        txtError = findViewById(R.id.txt_error);
        ticketView = findViewById(R.id.layout_ticket);
        progressBar = findViewById(R.id.progressBar);

        facade = new Facade();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idFilme = extras.getInt("idFilme");
            Filme filme = facade.detalheFilme(idFilme);
            ArrayList<Creditos> creditos = facade.creditosFilme(idFilme);
            for (int i = 0; i < creditos.size(); i++) {
                if(creditos.get(i).getJob().equals("Director")){
                    txtDirector.setText(creditos.get(i).getName());
                }
            }

            txtName.setText(filme.getTitle());
            txtDuration.setText(filme.getOverview());
            // loading album cover using Glide library
            Glide.with(this)
                    .load(Constants.URLIMAGEM + filme.getBackdrop_path())
                    .into(imgPoster);
            ticketView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent it = new Intent(getBaseContext(), MovieActivity.class);
        startActivity(it);
        finish();
    }
}
