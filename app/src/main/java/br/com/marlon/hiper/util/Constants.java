package br.com.marlon.hiper.util;

public class Constants {

    public static final String APIKEY = "?api_key=b62492831ba70a09a75a975f23e85216";
    public static final String LANGUAGEBR = "&language=pt-BR";
    public static final String URL = "https://api.themoviedb.org/3";

    //BUSCA
    public static final String URLBUSCA = URL +"/search/movie" + APIKEY + LANGUAGEBR + "&query=" ;

    //FILMES POPULARES
    public static final String URLPOPULAR = URL +"/movie/popular" + APIKEY + LANGUAGEBR;

    //FILMES DETALHES
    public static final String URLDETALHES = URL +"/movie/";
    public static final String URLCREDITOS = "/credits" + APIKEY;


    //IMAGENS
    public static final String URLIMAGEM = "https://image.tmdb.org/t/p/w500";
}