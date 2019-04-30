package app.countryapp.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "dbPaises";
    private static final int VERSAO_BANCO = 2;
    public static final String COLUNA_ID = "_id";
    public static final String TABELA_PAIS = "paises_tabela";
    public static final String COLUNA_PAIS = "pais";
    public static final String COLUNA_CAPITAL = "capital";
    public static final String COLUNA_REGIAO = "regiao";
    public static final String COLUNA_SUBREGIAO = "subregiao";
    public static final String COLUNA_POPULACAO = "populacao";
    public static final String COLUNA_LATLNG = "latlng";

    public SQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABELA_PAIS + " ( " +
                        COLUNA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        COLUNA_PAIS + " TEXT, " +
                        COLUNA_CAPITAL + " TEXT, " +
                        COLUNA_REGIAO + " TEXT, " +
                        COLUNA_SUBREGIAO + " TEXT, " +
                        COLUNA_POPULACAO + " TEXT, " +
                        COLUNA_LATLNG + " TEXT) "
        );

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // para as próximas versões
    }
}
