package br.com.marlon.hiper.request;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.marlon.hiper.model.Creditos;
import br.com.marlon.hiper.model.Filme;
import br.com.marlon.hiper.util.Constants;

/**
 * Created by Marlon on 28/10/2017.
 */

public class MovieRequest extends BaseRequest {

    private String strJson;
    private JSONObject jsonBody;
    private JSONObject jsonReturnApi;
    private Gson gson;

    public ArrayList<Filme> popularFilmes()
    {
        try{

            setUrl(Constants.URLPOPULAR);
            setMethod(Method.GET);

            strJson = this.execute(this).get();

            jsonBody = new JSONObject(strJson);

            if (Integer.valueOf(jsonBody.get("total_results").toString()) == 0)
            {
                return null;
            }
            else
            {
                JSONArray jArray = jsonBody.getJSONArray("results");
                ArrayList<Filme> popularFilmesArrayList = new ArrayList<>();

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);

                    Filme filme = new Filme();
                    filme.setId(jsonObject.getInt("id"));
                    filme.setTitle(jsonObject.getString("title"));
                    filme.setOverview(jsonObject.getString("overview"));
                    filme.setBackdrop_path(jsonObject.getString("backdrop_path"));
                    filme.setPoster_path(jsonObject.getString("poster_path"));
                    filme.setRelease_date(jsonObject.getString("release_date"));


                    popularFilmesArrayList.add(filme);
                }

                return popularFilmesArrayList;
            }

        }
        catch (Exception e)
        {
            return null;
        }
    }

    public ArrayList<Filme> buscaFilmes(String query)
    {
        try{

            setUrl(Constants.URLBUSCA + query);
            setMethod(Method.GET);

            strJson = this.execute(this).get();

            jsonBody = new JSONObject(strJson);

            if (Integer.valueOf(jsonBody.get("total_results").toString()) == 0)
            {
                return null;
            }
            else
            {
                JSONArray jArray = jsonBody.getJSONArray("results");
                ArrayList<Filme> buscaFilmesArrayList = new ArrayList<>();

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);

                    Filme filme = new Filme();
                    filme.setId(jsonObject.getInt("id"));
                    filme.setTitle(jsonObject.getString("title"));
                    filme.setOverview(jsonObject.getString("overview"));
                    filme.setBackdrop_path(jsonObject.getString("backdrop_path"));
                    filme.setPoster_path(jsonObject.getString("poster_path"));
                    filme.setRelease_date(jsonObject.getString("release_date"));


                    buscaFilmesArrayList.add(filme);
                }

                return buscaFilmesArrayList;
            }

        }
        catch (Exception e)
        {
            return null;
        }
    }

    public Filme detalheFilme(int id)
    {
        try{


            setUrl(Constants.URLDETALHES + id + Constants.APIKEY + Constants.LANGUAGEBR);
            setMethod(Method.GET);

            strJson = this.execute(this).get();

            jsonBody = new JSONObject(strJson);

            if (jsonBody == null)
            {
                return null;
            }
            else
            {
                gson = new Gson();
                Filme filme = new Filme();
                filme.setBackdrop_path(jsonBody.getString("backdrop_path"));
                filme.setTitle(jsonBody.getString("title"));
                filme.setOverview(jsonBody.getString("overview"));



//                detalheRetorno = gson.fromJson(jsonBody.getJSONObject("object").toString(), Filme.class);

                return filme;
            }

        }
        catch (Exception e)
        {
            return null;
        }
    }

    public ArrayList<Creditos> creditosFilme(int id)
    {
        try{

            setUrl(Constants.URLDETALHES + id + Constants.URLCREDITOS);
            setMethod(Method.GET);

            strJson = this.execute(this).get();

            jsonBody = new JSONObject(strJson);

            if (jsonBody == null)
            {
                return null;
            }
            else
            {
                JSONArray jArray = jsonBody.getJSONArray("crew");
                ArrayList<Creditos> creditosFilmesArrayList = new ArrayList<>();

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);

                    Creditos creditos = new Creditos();
                    creditos.setName(jsonObject.getString("name"));
                    creditos.setJob(jsonObject.getString("job"));
                    creditos.setDepartment(jsonObject.getString("department"));
                    creditos.setProfile_path(jsonObject.getString("profile_path"));

                    creditosFilmesArrayList.add(creditos);
                }

                return creditosFilmesArrayList;
            }

        }
        catch (Exception e)
        {
            return null;
        }
    }
}
