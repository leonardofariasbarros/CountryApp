package app.countryapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.countryapp.Adapter.Adapter;
import app.countryapp.Model.Paises;
import app.countryapp.Util.Http;
import app.countryapp.Util.Repositorio;

public class ListaPaisesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Adapter adapter;
    private List<Paises> paisesList;
    private List<Paises> paisesAUX;
    private ListView listView;
    private SwipeRefreshLayout swiperefresh;
    Repositorio db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_paises);

        swiperefresh = (SwipeRefreshLayout) findViewById((R.id.swiperefresh));
        swiperefresh.setColorScheme(R.color.colorPrimary, R.color.colorAccent);
        swiperefresh.setOnRefreshListener(this);

        listView = (ListView) findViewById(R.id.listView);
        paisesList = new ArrayList<Paises>();
        paisesAUX = new ArrayList<Paises>();

        adapter = new Adapter(this, paisesList);

        db = new Repositorio(getBaseContext());
        getDataHttp();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hasPermission();
                Intent intent = new Intent(ListaPaisesActivity.this, MapsActivity.class);
                intent.putExtra("pais",paisesList.get(position));
                startActivity(intent);
            }

        });
    }

    public void getDataHttp () {
        swiperefresh.setRefreshing(true);

        if (isConnected()) {
            PaisesTask mTask = new PaisesTask();
            mTask.execute();
            db.excluirAll();

        }else {
            swiperefresh.setRefreshing(false);
            Toast.makeText(this,"Sem Conexão, listando Ubs do banco...",Toast.LENGTH_SHORT).show();
            getDataSqlite();
        }

    }

    private void getDataSqlite() {
        paisesList.clear();
        paisesList.addAll(db.listarPaises());
        adapter.notifyDataSetChanged();
    }

    class PaisesTask extends AsyncTask<Void, Void, List<Paises>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swiperefresh.setRefreshing(true);
        }

        @Override
        protected List<Paises> doInBackground(Void... voids) {
            return Http.carregarPaisesJson();
        }

        @Override
        protected void onPostExecute(List<Paises> paises) {
            super.onPostExecute(paises);
            if (paises != null) {
                paisesList.clear();
                paisesList.addAll(paises);
                adapter.notifyDataSetChanged();
                Log.e("leo", paisesList.size()+"");
                for (int j = 0; j < paisesList.size(); j++) {
                    db.inserir(paisesList.get(j));
                }
            }
            swiperefresh.setRefreshing(false);
        }
    }


    public void onRefresh() {
        getDataHttp();
    }

    void hasPermission(){
        //pede permissao de localizacao
        if (ContextCompat.checkSelfPermission(ListaPaisesActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // ja pediu permissao?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ListaPaisesActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                // solicita permissao de localizacao
                ActivityCompat.requestPermissions(ListaPaisesActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }
    }

    public Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( cm != null ) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnected();
        }
        return false;
    }

}
