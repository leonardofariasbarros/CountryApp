package app.countryapp.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.countryapp.Model.Paises;

public class Http {
    public static final String PAIS_URL_JSON = "https://restcountries.eu/rest/v1/all";

    private static HttpURLConnection connectar(String urlArquivo) throws IOException {
        final int SEGUNDOS = 1000;
        URL url = new URL(urlArquivo);
        HttpURLConnection conexao = (HttpURLConnection)url.openConnection();
        conexao.setReadTimeout(10 * SEGUNDOS);
        conexao.setConnectTimeout(15 * SEGUNDOS);
        conexao.setRequestMethod("GET");
        conexao.setDoInput(true);
        conexao.setDoOutput(false);
        conexao.connect();
        return conexao;
    }

    public static List<Paises> carregarPaisesJson() {
        try {
            HttpURLConnection conexao = connectar(PAIS_URL_JSON);
            int resposta = conexao.getResponseCode();
            if (resposta ==  HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                JSONArray json = new JSONArray(bytesParaString(is));
                return lerJsonPaises(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Paises> carregarAmerica() {
        try {
            HttpURLConnection conexao = connectar(PAIS_URL_JSON);
            int resposta = conexao.getResponseCode();
            if (resposta ==  HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                JSONArray json = new JSONArray(bytesParaString(is));
                return listaPaisesAmerica(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesParaString(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        // O bufferzao vai armazenar todos os bytes lidos
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        // precisamos saber quantos bytes foram lidos
        int bytesLidos;
        // Vamos lendo de 1KB por vez...
        while ((bytesLidos = is.read(buffer)) != -1) {
            // copiando a quantidade de bytes lidos do buffer para o bufferzão
            bufferzao.write(buffer, 0, bytesLidos);
        }
        return new String(bufferzao.toByteArray(), "UTF-8");
    }

    private static List<Paises> lerJsonPaises(JSONArray json) throws JSONException {
        List<Paises> listaDePaises = new ArrayList<Paises>();

        for (int j = 0; j < json.length(); j++) {
            JSONObject unidade = json.getJSONObject(j);
            Paises paises = new Paises(j,
                    unidade.getString("name"),
                    unidade.getString("capital"),
                    unidade.getString("region"),
                    unidade.getString("subregion"),
                    unidade.getString("population"),
                    unidade.getString("latlng")
            );


            listaDePaises.add(paises);
        }

        return listaDePaises;
    }

    private static List<Paises> listaPaisesAmerica(JSONArray json) throws JSONException {
        List<Paises> listaDePaises = new ArrayList<Paises>();

        for (int j = 0; j < json.length(); j++) {

            JSONObject unidade = json.getJSONObject(j);
            Paises paises = new Paises(j,
                    unidade.getString("name"),
                    unidade.getString("capital"),
                    unidade.getString("region"),
                    unidade.getString("subregion"),
                    unidade.getString("population"),
                    unidade.getString("latlng")
            );

            if(paises.getSubregiao().contains("South America")){
                listaDePaises.add(paises);
            }
        }

        return listaDePaises;
    }

}
