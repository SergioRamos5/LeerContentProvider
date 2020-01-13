package com.example.leercontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    ContentResolver cr;
    EditText texto;
    Button consultar, añadir, eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = findViewById(R.id.EditText);

        consultar = findViewById(R.id.Consultar);
        añadir = findViewById(R.id.Añadir);
        eliminar = findViewById(R.id.Eliminar);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = consultar();
                recorrerCursor(c);
            }
        });

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
    }

    private Cursor consultar()
    {
        String[] projection = new String[]{"nombre", "telefono", "email"};
        cr = getContentResolver();
        Cursor cur = cr.query(Uri.parse("content://com.example.contentprovider/clientes"), projection,null,null,null);
        return  cur;
    }

    private void recorrerCursor(Cursor cur)
    {
        cur.moveToFirst();
        texto.setText("");
        do {
            texto.setText(texto.getText().toString() + cur.getString(cur.getColumnIndex("nombre"))+" "+
                    cur.getString(cur.getColumnIndex("telefono")) + "\n");
            cur.moveToNext();
        }while (!cur.isLast());

        texto.setText(texto.getText().toString() + cur.getString(cur.getColumnIndex("nombre"))+" "+
                cur.getString(cur.getColumnIndex("telefono")) + "\n");
    }

    private void insertar()
    {
        cr = getContentResolver();
        ContentValues x = new ContentValues();
        x.put("nombre", "ANA ANITA");
        x.put("telefono", "4444444444");
        x.put("email", "adhsufh@gmail.com");
        cr.insert(Uri.parse("content://com.example.contentprovider/Clientes"),x);
    }

    private void eliminar()
    {
        cr = getContentResolver();
        cr.delete(Uri.parse("content://com.example.contentprovider/clientes"), "nombre" + "= 'ANA ANITA'", null);
    }

}
