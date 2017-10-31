package br.com.marlon.hiper.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.marlon.hiper.R;
import br.com.marlon.hiper.adapter.MovieAdapter;
import br.com.marlon.hiper.facade.Facade;
import br.com.marlon.hiper.util.RecyclerItemClickListener;

public class MovieActivity extends AppCompatActivity {

    private RecyclerView rvFilmesPop, rvFilmes;
    private MovieAdapter adapter;
    private Facade facade;
    private int idFilme;

    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        facade = new Facade();
        rvFilmesPop = (RecyclerView) findViewById(R.id.rvFilmesPop);
        rvFilmes = (RecyclerView) findViewById(R.id.rvFilmes);
        adapter = new MovieAdapter(MovieActivity.this, facade.popularFilmes());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        rvFilmesPop.setLayoutManager(mLayoutManager);
        rvFilmesPop.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvFilmesPop.setItemAnimator(new DefaultItemAnimator());
        rvFilmesPop.setAdapter(adapter);
        rvFilmesPop.addOnItemTouchListener(
                new RecyclerItemClickListener(getBaseContext(), rvFilmesPop, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        idFilme = facade.popularFilmes().get(position).getId();
                        Intent intent = new Intent(MovieActivity.this, MovieInternaActivity.class);
                        Bundle extras = new Bundle();
                        intent.putExtra("idFilme", idFilme);
                        intent.putExtras(extras);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

    }


    /**
     * RecyclerView item decoration
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Convertendo dp para pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        searchView.setFocusable(true);
        searchView.setQueryHint("Buscar filme");
        searchView.clearFocus();
        searchView.setOnQueryTextListener(onSearch());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private SearchView.OnQueryTextListener onSearch(){
        return new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(final String query){
                // Usuario fez a busca
                Log.i("Busca ", query);
                    if (!query.equals(""))
                    {
                        facade = new Facade();
                        if (facade.verificarConexao(getBaseContext())) {
                            adapter = new MovieAdapter(MovieActivity.this, facade.buscaFilmes(query));

                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MovieActivity.this, 2);
                            rvFilmes.setLayoutManager(mLayoutManager);
                            rvFilmes.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                            rvFilmes.setItemAnimator(new DefaultItemAnimator());
                            rvFilmesPop.setVisibility(View.GONE);
                            rvFilmes.setVisibility(View.VISIBLE);
                            rvFilmes.setAdapter(adapter);
                            rvFilmes.addOnItemTouchListener(
                                    new RecyclerItemClickListener(getBaseContext(), rvFilmes, new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            idFilme = facade.buscaFilmes(query).get(position).getId();
                                            Intent intent = new Intent(MovieActivity.this, MovieInternaActivity.class);
                                            Bundle extras = new Bundle();
                                            intent.putExtra("idFilme", idFilme);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {

                                        }
                                    }));

                        } else {
                            new AlertDialog.Builder(MovieActivity.this)
                                    // Set Dialog Icon
                                    // Set Dialog Title
                                    .setCancelable(false)
                                    // Set Dialog Message
                                    .setMessage("Por favor, verifique sua conexão com a internet.")

                                    // Positive button
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                    }

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText){
                //
                return false;
            }
        };
    }
}
