package com.example.laboratorio3;

import android.provider.BaseColumns;

public class ContactoContract {

    public static class ContactoEntry implements BaseColumns{
        public static final String COLUMN_NAME_NAME = "nombre";
        public static final String COLUMN_NAME_APELLIDOS = "apellidos";
        public static final String COLUMN_NAME_PHONE = "telefono";
        public static final String COLUMN_NAME_SEX = "sexo";
        public static final String TABLE_NAME = "tblContacto";
    }
}
