package app.countryapp.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import app.countryapp.Dao.SQLHelper;
import app.countryapp.Model.Paises;

public class Repositorio {

    private SQLHelper helper;
    private SQLiteDatabase db;

    public Repositorio(Context ctx){
        helper = new SQLHelper(ctx);
    }

    public long inserir(Paises paises){
        db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLHelper.COLUNA_PAIS, paises.pais);
        cv.put(SQLHelper.COLUNA_CAPITAL, paises.capital);
        cv.put(SQLHelper.COLUNA_REGIAO, paises.regiao);
        cv.put(SQLHelper.COLUNA_SUBREGIAO, paises.subregiao);
        cv.put(SQLHelper.COLUNA_POPULACAO, paises.populacao);
        cv.put(SQLHelper.COLUNA_LATLNG, paises.latlng);

        long id = db.insert(SQLHelper.TABELA_PAIS, null, cv);

        if(id != -1){
            paises.id = id;
        }

        db.close();

        return id;
    }

    public void excluirAll(){
        db = helper.getWritableDatabase();
        db.delete(SQLHelper.TABELA_PAIS, null, null);
        db.close();
    }

    public List<Paises> listarPaises() {
        String sql = "SELECT * FROM " + SQLHelper.TABELA_PAIS;
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Paises> list = new ArrayList();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(
                    cursor.getColumnIndex(SQLHelper.COLUNA_ID)
            );
            String pais = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_PAIS)
            );
            String capital = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_CAPITAL)
            );
            String regiao = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_REGIAO)
            );
            String subregiao = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_SUBREGIAO)
            );
            String populacao = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_POPULACAO)
            );
            String latlng = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_LATLNG)
            );

            Paises paises = new Paises(id,pais, capital, regiao,subregiao, populacao, latlng);
            list.add(paises);
        }
        cursor.close();
        return list;
    }
}
