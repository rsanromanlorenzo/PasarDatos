package com.example.robert.pasardatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Editar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        final Persona pers=(Persona)getIntent().getExtras().getSerializable("contacto");
        final int puesto=getIntent().getExtras().getInt("posicion");
        final EditText ednom=(EditText)findViewById(R.id.nomeed);
        final EditText edTelf=(EditText)findViewById(R.id.telefonoed);
        ednom.setText(pers.getNombre());
        edTelf.setText(String.valueOf(pers.getNumero()));
        final Button btnok=(Button)findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new View.OnClickListener() {

                                     public void onClick(View v) {
                                         //aquí ponemos el código que se ejecutará al realizar un click (evento onclick):
                                         //Intents son los elementos encargados de realizar la comunicación entre las distinas activitys, para pasar de unas
                                         //a otras,...
                                         pers.setNombre(ednom.getText().toString());
                                         pers.setNumero(Integer.parseInt(edTelf.getText().toString()));
                                         Intent inted=new Intent();
                                         inted.putExtra("contacto",pers);
                                         inted.putExtra("posicion",puesto);
                                         setResult(RESULT_OK,inted);
                                         finish();


                                     }
                                 }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
