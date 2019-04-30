package app.countryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.countryapp.Model.Paises;
import app.countryapp.R;

public class Adapter extends ArrayAdapter<Paises> {

    public Adapter(Context context, List<Paises> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Paises paises = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_paises, parent, false);
        }
        TextView tvNome = (TextView) convertView.findViewById(R.id.pais);
        TextView tvBairro = (TextView) convertView.findViewById(R.id.capital);
        tvNome.setText(paises.pais);
        tvBairro.setText(paises.capital);
        return convertView;
    }

}