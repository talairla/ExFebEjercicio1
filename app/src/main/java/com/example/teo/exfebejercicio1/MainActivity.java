package com.example.teo.exfebejercicio1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String tag="ExFeb1";
    /*Dados los siguientes Array de Strings*/
    /*Valor total del ejercicio: 35pt*/
    String nombres[] = { "Juan", "Pedro" , "Marcelo", "Tomás", "Manuela", "José", "María", "Benito"};
    String apellidos[] = { "Pérez", "García", "López", "Jiménez" };

    ListView listView;
    SQLiteDatabase db;
    Button btnRellenar;


    /*1.- Cada vez que obtenga el foco el activity, hay que generar y rellenar una tabla SQLite
          con 50 registros que contengan los siguientes campos (la tabla deberá borrarse
          y crearse de nuevo cada vez que el activity obtenga el foco):
            -Nombre al azar entre los del array, -apellido al azar entre los del array, -edad al azar entre 20 y 70. (15pt)
      2.- Cada vez que se pulse el botón con id btnRellenar hay que rellenar el ListView lvPersonas con toda la información de las
          personas almacenadas en la tabla SQLite que en el nombre y en el apellido contengan una 'a' ordenados por la edad. (10pt)
          Cada elemento de la lista debe ser un conjunto de 3 TextViews dispuestos uno encima de otro con los campos Apellido, Nombre y Edad. (10pt)
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        btnRellenar = (Button) findViewById(R.id.btnRellenar);
        btnRellenar.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        createDB();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnRellenar){
            String query = "" +
                    "SELECT * " +
                    "FROM nombres " +
                    "WHERE nombre LIKE '%a%' " +
                    " AND apellido LIKE '%a%' " +
                    "ORDER BY edad;";

            Cursor c = db.rawQuery(query,null);

            ArrayList<Persona> lista_personas = new ArrayList<Persona>();

            if (c.moveToFirst()){
                do{
                    Persona p = new Persona(c.getString(0),c.getString(1),Integer.valueOf(c.getString(2)));

                    lista_personas.add(p);
                }while (c.moveToNext());

                ListViewAdapter adapter = new ListViewAdapter(this, R.layout.fila_list_view, lista_personas);

                listView.setAdapter(adapter);
            }

        }
    }

    private void createDB(){
        db = openOrCreateDatabase("personas", getApplicationContext().MODE_PRIVATE, null);

        String queryCreate = "" +
                "CREATE TABLE IF NOT EXISTS nombres(" +
                    "nombre VARCHAR(50), "+
                    "apellido VARCHAR(50), "+
                    "edad INTEGER);";

        String queryDropTable = "DROP TABLE IF EXISTS nombres";

        db.execSQL(queryDropTable);
        db.execSQL(queryCreate);

        for(int i = 0; i<50; i++){
            Random rand = new Random();
            int aleatorioNombres = rand.nextInt(nombres.length);
            String nombre = nombres[aleatorioNombres];
            Log.i(tag,"Seleccionado nombre:" + nombre);
            int aleatorioApellidos = rand.nextInt(apellidos.length);
            String apellido = apellidos[aleatorioApellidos];
            Log.i(tag,"Seleccionado apellido:" + apellido);

            int edad = rand.nextInt(50) + 20;
            //edad = (int) Math.random()*50 + 20;
            Log.i(tag,"Seleccionada edad:"+edad);
            String queryInsert = "" +
                    "INSERT INTO nombres VALUES ( '" +
                    nombre+"','"+
                    apellido+"',"+edad+");";
            db.execSQL(queryInsert);
        }

    }
}
