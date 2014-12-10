package com.example.robert.pasardatos;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ROBERT on 06/12/2014.
 */
public class MainActivity extends Activity {

    ArrayList<Persona> agendas = new ArrayList<Persona>();
    ListView lstAgenda;
    ArrayAdapter<String> adaptador2;
    String[] nome;


    protected void onCreate(Bundle savedInstanceState) {
        //orden para que me guarde el estado de la aplicación
        super.onCreate(savedInstanceState);

        //Indico cual es el layout al que haré referencia (en este caso AGENDA)
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.add);
        //Poñemos os listner a os editText para que ao pulsalos limpemos o texto anterior

        final EditText nom = (EditText) findViewById(R.id.nome);
        final EditText tlf = (EditText) findViewById(R.id.telefono);
        //CREO EL LISTÍN TELEFÓNICO:
        //meto un par de contactos primeiro:
        agendas.add(new Persona("manolo", 667246629));
        agendas.add(new Persona("Juan", 667556629));
        nome = new String[agendas.size()];
        for (int i = 0; i < agendas.size(); i++) {
            nome[i] = agendas.get(i).getNombre() + "\n" + agendas.get(i).getNumero();
        }
        //****CONTROL LISTVIEW (El mas utilizado en Android)**************

        adaptador2 =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, nome);
        //Asigno referencia a lista e logo asignolle o adaptador
        lstAgenda = (ListView) findViewById(R.id.LstAgenda);
        lstAgenda.setAdapter(adaptador2);

//***************MENU CONTEXTUAL*****************
        //Asociamos los menús contextuales a los controles
        registerForContextMenu(lstAgenda);


        //***********IMPLEMENTO EL MÉTODO PARA EL BOTON ADD**********
        btn.setOnClickListener(new View.OnClickListener() {

                                   public void onClick(View v) {
                                       //aquí ponemos el código que se ejecutará al realizar un click (evento onclick):
                                       //Intents son los elementos encargados de realizar la comunicación entre las distinas activitys, para pasar de unas
                                       //a otras,...
                                       agendas.add(new Persona(nom.getText().toString(), Integer.parseInt(tlf.getText().toString())));
                                       nom.setText("");
                                       tlf.setText("");

                                       actualizaListView(adaptador2);


                                   }
                               }
        );


    }


    //************SOBREESCRIBO O METODO PARA O MENU CONTEXTUAL
    //Info quitada de: http://www.sgoliver.net/blog/?p=1784
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle(
                lstAgenda.getAdapter().getItem(info.position).toString());

        inflater.inflate(R.menu.menu_context, menu);
    }

    //*************IMPLEMENTO ACCIONS PARA OS EVENTOS DO MENU CONTEXTUAL
    //Infol quitada de: http://www.sgoliver.net/blog/?p=1784
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.CtxLblOpc1:
                showToast("Menu Edicion");
                Intent Inted = new Intent(MainActivity.this, Editar.class);
                Inted.putExtra("contacto", agendas.get(info.position));
                Inted.putExtra("posicion", info.position);
                startActivityForResult(Inted, 1);
                return true;
            case R.id.CtxLblOpc2:
                agendas.remove(info.position);
                showToast("Contacto Borrado");
                actualizaListView(adaptador2);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //********IMPLEMENTO MÉTODO ONACTIVIYRESULT

    //Recibo info do contacto editado
    public void onActivityResult(int requestCode, int resultCode, Intent datos) {
        //Obtengo el objeto Persona y su posición editados
        Persona contacto = (Persona) datos.getExtras().getSerializable("contacto");
        int puesto = datos.getExtras().getInt("posicion");
        //modifico o arraylist e actualizo o listView
        agendas.get(puesto).setNombre(contacto.getNombre());
        agendas.get(puesto).setNumero(contacto.getNumero());
        showToast("Actualizado");
        actualizaListView(adaptador2);


    }


    //Creo método para que cada vez que cambio algo do listView, se me actualice (refrescar)
    private void actualizaListView(ArrayAdapter<String> adaptador) {
        nome = new String[agendas.size()];
        for (int j = 0; j < agendas.size(); j++) {
            nome[j] = agendas.get(j).getNombre() + "\n" + agendas.get(j).getNumero();
        }


        adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, nome);

        lstAgenda.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
    }

    protected void showToast(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
    }
}