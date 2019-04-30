package app.countryapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.countryapp.Model.Paises;
import app.countryapp.Util.Http;

public class MainActivity extends AppCompatActivity{

    private List<Paises> paisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button verMapa = (Button)findViewById(R.id.btnMapa);
        Button listaPaises = (Button)findViewById(R.id.btnLista);

        paisesList = new ArrayList<Paises>();

        getDataHttp ();

        verMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity2.class);
                intent.putExtra("paises", (Serializable) paisesList);
                startActivity(intent);
            }
        });

        listaPaises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListaPaisesActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getDataHttp () {
        PaisesTask mTask = new PaisesTask();
        mTask.execute();
    }

    class PaisesTask extends AsyncTask<Void, Void, List<Paises>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Paises> doInBackground(Void... voids) {
            return Http.carregarAmerica();
        }

        @Override
        protected void onPostExecute(List<Paises> paises) {
            super.onPostExecute(paises);
            if (paises != null) {
                paisesList.clear();
                paisesList.addAll(paises);
            }
        }
    }

    public void onRefresh() {
        getDataHttp();
    }
}

