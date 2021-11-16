package com.example.laboratorio3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.laboratorio3.R;
import com.example.laboratorio3.models.Contacto;

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

        View v = convertView;
        Contacto contacto = objects.get(position);
        ImageView imguser = (ImageView)v.findViewById(android.R.id.icon);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.contato_item, null);
        TextView tvNombre = view.findViewById(R.id.tvNombreItem);
        TextView tvApellidos = view.findViewById(R.id.tvApellidosItem);

        String nombre = contacto.getNombre();
        String apellidos = contacto.getPaterno()+ " " + contacto.getMaterno();

        tvNombre.setText(nombre);
        tvApellidos.setText(apellidos);

        if(imguser != null) {
            if (contacto.getSexo() == 0)
                imguser.setImageResource(R.drawable.usuario_hombre);
            else if(contacto.getSexo() == 1)
                imguser.setImageResource(R.drawable.usuario_mujer);
        }

        return view;
    }
}
