package br.com.marlon.hiper.facade;

import android.content.Context;

import java.util.ArrayList;

import br.com.marlon.hiper.model.Creditos;
import br.com.marlon.hiper.model.Filme;
import br.com.marlon.hiper.request.MovieRequest;
import br.com.marlon.hiper.util.Internet;


/**
 * @author Marlon Santini
 */
public class Facade implements IFacade {


    /**
     * Metodos MovieRequest
     */

    @Override
    public ArrayList<Filme> popularFilmes() {
        MovieRequest movieRequest = new MovieRequest();
        return movieRequest.popularFilmes();
    }

    @Override
    public ArrayList<Filme> buscaFilmes(String query) {
        MovieRequest movieRequest = new MovieRequest();
        return movieRequest.buscaFilmes(query);
    }

    @Override
    public Filme detalheFilme(int id) {
        MovieRequest movieRequest = new MovieRequest();
        return movieRequest.detalheFilme(id);
    }

    @Override
    public ArrayList<Creditos> creditosFilme(int id) {
        MovieRequest movieRequest = new MovieRequest();
        return movieRequest.creditosFilme(id);
    }


    /**
     * Outros Metodos
     */

    @Override
    public boolean verificarConexao(Context context) {
        Internet internet = new Internet(context);
        return internet.verificarConexao();
    }

}
