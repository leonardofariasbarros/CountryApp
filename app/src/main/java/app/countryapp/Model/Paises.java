package app.countryapp.Model;

import java.io.Serializable;

public class Paises implements Serializable {

    public long id;
    public String pais;
    public String capital;
    public String regiao;
    public String subregiao;
    public String populacao;
    public String latlng;

    public Paises(long id,String pais, String capital, String regiao,String subregiao, String populacao, String latlng) {
        this.id = id;
        this.pais = pais;
        this.capital = capital;
        this.regiao = regiao;
        this.subregiao = subregiao;
        this.populacao = populacao;
        this.latlng = latlng;
    }

    @Override
    public String toString() {
        //lat = latlng.substring(2,latlng.indexOf(','));
        //lng = latlng.substring(latlng.indexOf(',')+1,latlng.indexOf(']'));

        return  pais +". Capital: " + capital+". População: "+populacao;
    }

    public long getId() { return id; }

    public String getPais() {
        return pais;
    }

    public String getCapital() {
        return capital;
    }

    public String getRegiao() {
        return regiao;
    }

    public String getSubregiao() { return subregiao; }

    public String getPopulacao() {
        return populacao;
    }

    public String getLatlng() {
        return latlng;
    }

}