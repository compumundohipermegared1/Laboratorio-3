package com.example.laboratorio3;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactoAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<Contacto> items;
	private int layout;
    
    public ContactoAdapter() { }
    
	public ContactoAdapter(Context ctx, int layout,  ArrayList<Contacto> items) {
		this.ctx = ctx;
		this.items = items;
		this.layout = layout;
	}

	@Override
	public int getCount() {
		return items.size();
	}
	@Override
	public Contacto getItem(int arg0) {
		return items.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		return items.get(arg0).getId();
	}
	@Override
	public View getView(int arg0, View convertView, ViewGroup parent) {

		View v = convertView;
		
		if(convertView == null){
			LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(layout, null);
		}
		
		Contacto r = getItem(arg0);
		ImageView imguser = (ImageView)v.findViewById(android.R.id.icon);
		TextView nombre = (TextView)v.findViewById(R.id.text1);
		TextView apellidos = (TextView)v.findViewById(R.id.text2);
		TextView telefono = (TextView)v.findViewById(R.id.text3);

		if(imguser != null) {
			if (r.getSexo() == 0)
				imguser.setImageResource(R.drawable.masculino);
			else if(r.getSexo() == 1)
				imguser.setImageResource(R.drawable.femenino);
			else if(r.getSexo() == 2)
				imguser.setImageResource(R.drawable.non_binary);
		}

		if(nombre != null)
			nombre.setText(r.getNombre());
		if(apellidos != null)
			apellidos.setText(r.getApellidos());
		if(telefono != null)
			telefono.setText(r.getTelefono());

		return v;
	}
}
