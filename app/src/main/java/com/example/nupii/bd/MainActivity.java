package com.example.nupii.bd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    EditText et_idalumno, et_nombre, et_apellidop, et_apellidom, et_carrera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_idalumno = (EditText) findViewById(R.id.et_idalumno);
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_apellidop = (EditText) findViewById(R.id.et_apellidop);
        et_apellidom = (EditText) findViewById(R.id.et_apellidom);
        et_carrera = (EditText) findViewById(R.id.et_carrera);

    }

    public void alta (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String idalumno = et_idalumno.getText().toString();
        String nombre = et_nombre.getText().toString();
        String apellidop = et_apellidop.getText().toString();
        String apellidom = et_apellidom.getText().toString();
        String carrera = et_carrera.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("id_alumno", idalumno);
        registro.put("nombre", nombre);
        registro.put("apellido_p", apellidop);
        registro.put("apellido_m", apellidom);
        registro.put("carrera", carrera);

        bd.insert("alumnos", null, registro);
        bd.close();

        et_idalumno.setText("");
        et_nombre.setText("");
        et_apellidop.setText("");
        et_apellidom.setText("");
        et_carrera.setText("");

        Toast.makeText(this,"Se agrego un nuevo alumno",Toast.LENGTH_SHORT).show();

    }

    public void consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String idalumno = et_idalumno.getText().toString();
        Cursor fila = bd.rawQuery("select  nombre, apellido_p, apellido_m, carrera from alumnos where id_alumno=" + idalumno, null);
        if (fila.moveToFirst()) {

            et_nombre.setText(fila.getString(0));
            et_apellidop.setText(fila.getString(1));
            et_apellidom.setText(fila.getString(2));
            et_carrera.setText(fila.getString(3));
        } else {
            Toast.makeText(this,"No existe el alumno",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void baja(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String idalumno = et_idalumno.getText().toString();
        int cant = bd.delete("alumnos","id_alumno=" + idalumno, null);
        bd.close();

        et_idalumno.setText("");
        et_nombre.setText("");
        et_apellidop.setText("");
        et_apellidom.setText("");
        et_carrera.setText("");

        if (cant == 1) {
            Toast.makeText(this, "Se borró el alumno",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el alumno",Toast.LENGTH_SHORT).show();
        }
    }

    public void modificacion (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String idalumno = et_idalumno.getText().toString();
        String nombre = et_nombre.getText().toString();
        String apellidop = et_apellidop.getText().toString();
        String apellidom = et_apellidom.getText().toString();
        String carrera = et_carrera.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("id_alumno", idalumno );
        registro.put("nombre", nombre);
        registro.put("apellido_p", apellidop);
        registro.put("apellido_m", apellidom);
        registro.put("carrera", carrera);

        int cant = bd.update("alumnos", registro, "id_alumno=" + idalumno, null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Se modificaron los datos del alumno",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el alumno",Toast.LENGTH_SHORT).show();
        }

    }

    public void limpia (View v){
        et_idalumno.setText("");
        et_nombre.setText("");
        et_apellidop.setText("");
        et_apellidom.setText("");
        et_carrera.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
