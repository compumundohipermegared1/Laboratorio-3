package com.example.listaelementos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.listaelementos.R;
import com.example.listaelementos.models.Contacto;

import java.util.List;

public class ContactoAdapter extends ArrayAdapter<Contacto> {

    Context context;
    List<Contacto> objects;

    public ContactoAdapter(@NonNull Context context, int resource, @NonNull List<Contacto> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contacto contacto = objects.get(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.contato_item, null);
        TextView tvNombre = view.findViewById(R.id.tvNombreItem);
        TextView tvApellidos = view.findViewById(R.id.tvApellidosItem);

        String nombre = contacto.getNombre();
        String apellidos = contacto.getPaterno()+ " " + contacto.getMaterno();

        tvNombre.setText(nombre);
        tvApellidos.setText(apellidos);

        return view;
    }
}
