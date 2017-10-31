package br.com.marlon.hiper.facade;

import android.content.Context;

import java.util.ArrayList;

import br.com.marlon.hiper.model.Creditos;
import br.com.marlon.hiper.model.Filme;


/**
 * @author Marlon Santini
 */
public interface IFacade {

    /**
     * Metodos MovieRequest
     */

    ArrayList<Filme> popularFilmes();
    ArrayList<Filme> buscaFilmes(String query);
    Filme detalheFilme(int id);
    ArrayList<Creditos> creditosFilme(int id);




    /**
     * Outros Metodos
     */

    boolean verificarConexao(Context context);



}
