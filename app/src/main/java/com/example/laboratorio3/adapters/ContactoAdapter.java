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
    private int resource;
    List<Contacto> objects;

    public ContactoAdapter(@NonNull Context context, int resource, @NonNull List<Contacto> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    public Contacto getObject(int position) {
        return objects.get(position);
    }

    public long getObjectId(int position) {
        return objects.get(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(resource, null);
        }

        Contacto contacto = getObject(position);
        ImageView imguser = (ImageView)v.findViewById(android.R.id.icon);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_detalle, null);
        TextView tvNombre = view.findViewById(R.id.tvNombre);
        TextView tvPaterno = view.findViewById(R.id.tvPaterno);
        TextView tvMaterno = view.findViewById(R.id.tvMaterno);
        TextView tvTelefono = view.findViewById(R.id.tvTelefono);

        String nombre = contacto.getNombre();
        String apellidos = contacto.getPaterno()+ " " + contacto.getMaterno();
        String telefono = contacto.getTelefono();

        /*

        if(nombre != null)
            nombre.setText(contacto.getNombre());
        if(telefono != null)
            telefono.setText(contacto.getTelefono());

         */

        if(imguser != null) {
            if (contacto.getSexo() == 0)
                imguser.setImageResource(R.drawable.usuario_masculino);
            else if(contacto.getSexo() == 1)
                imguser.setImageResource(R.drawable.usuario_femenino);
        }

        return view;
    }
}
