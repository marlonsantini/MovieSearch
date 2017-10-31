package br.com.marlon.hiper.request;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class BaseRequest extends AsyncTask<BaseRequest, Object, String> {

    private String url;
    private JSONObject jsonObject;
    private Method method;
    private Context context;
    private String strReturn;
    private String jsonString;



    public BaseRequest() {

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setStrReturn(String strReturn) {
        this.strReturn = strReturn;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    protected String doInBackground(BaseRequest... params) {
        try {
            return sendGson(this.url, this.method, this.jsonString, this.context);
        } catch (JSONException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private static String sendGson(String apiUrl, Method method, String gsonString, Context context) throws JSONException, IOException {

        URL url;
        String returnStr = "";

        try {
            url = new URL(apiUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + apiUrl);
        }

        try {
            if (method == Method.POST) {


                /*
                HTTP
                 */
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();

                /*
                HTTPS
                 */
                //HttpsURLConnection conn = null;
                //conn = (HttpsURLConnection) url.openConnection();
                //conn.setSSLSocketFactory(generateCertificate(context).getSocketFactory());

                byte[] bytes = null;


                String body = "";
                if (gsonString != null) {
                    body = gsonString;
                }

                bytes = body.getBytes();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                //conn.setRequestProperty("Content-Type", "application/json");

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod(String.valueOf(method));
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream out = conn.getOutputStream();
                out.write(bytes);
                out.close();

                int status = conn.getResponseCode();

                InputStream is;
                String convertStreamToString = "";

                if (status == 400 || status == 500) {
                    //return MessageText.ERROR_SERVER.toString();
                    convertStreamToString = convertStreamToString(conn.getErrorStream(), "UTF-8");
                } else {
                    convertStreamToString = convertStreamToString(conn.getInputStream(), /*HTTP.UTF_8*/"UTF-8");
                    conn.disconnect();
                }
                //returnStr = convertStreamToString;
                return convertStreamToString;

            }
            else if (method == Method.GET)
            {
                url = new URL(apiUrl);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                String jsonStr = convertStreamToString(in, "UTF-8");

                returnStr = jsonStr;

                return jsonStr;
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return returnStr;
    }

    private static String convertStreamToString(InputStream is, String enc) throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, enc));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
