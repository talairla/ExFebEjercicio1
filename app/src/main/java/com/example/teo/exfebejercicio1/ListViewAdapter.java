package com.example.teo.exfebejercicio1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Teo on 06/03/2017.
 */

public class ListViewAdapter extends ArrayAdapter {

    List<Persona> lista_personas;


    public ListViewAdapter(Context context, int resource, List<Persona> objects) {
        super(context, resource, objects);
        lista_personas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return CreateView(position, convertView, parent);
    }

    private View CreateView(int posicion, View elementView, ViewGroup parent){
        LayoutInflater elementInflater = LayoutInflater.from(parent.getContext());
        View elemento = elementInflater.inflate(R.layout.fila_list_view, parent, false);
        TextView nombre = (TextView) elemento.findViewById(R.id.tvNombre);
        TextView apellido = (TextView) elemento.findViewById(R.id.tvApellido);
        TextView edad = (TextView) elemento.findViewById(R.id.tvEdad);

        Persona p = lista_personas.get(posicion);
        nombre.setText(p.getNombre());
        apellido.setText(p.getApellido());
        edad.setText(String.valueOf(p.edad));

        return elemento;
    }
}
