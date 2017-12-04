package com.gincana.enti.gincanaenti;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Hint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pista);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        //Set custom toolbar name.
        TextView name = (TextView)findViewById(R.id.appbar_name);
        name.setText("Add hint");
        //Floating button that adds the new hint.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get all the info introduced by the user.
                EditText ID = (EditText)findViewById(R.id.identificador);
                Spinner option = (Spinner)findViewById(R.id.spinner);
                EditText des = (EditText)findViewById(R.id.descripcion);
                EditText lat = (EditText)findViewById(R.id.latitud);
                EditText lon = (EditText)findViewById(R.id.longitud);
                EditText nextID = (EditText)findViewById(R.id.identificadorSiguiente);
                //Check if there's any empty field.
                if(ID.getText().toString().isEmpty() || des.getText().toString().isEmpty() || nextID.getText().toString().isEmpty() ||lat.getText().toString().isEmpty()|| lon.getText().toString().isEmpty() ) {
                    Toast.makeText(view.getContext(), "There can't be empty fields", Toast.LENGTH_SHORT).show();
                }else{
                    //Check if the hint to add already exist in the list.
                    if(ListaPistas.searchPista((ID.getText().toString())) == null){
                        //Create a new hint depending on the item selected int the spinner.
                        if(option.getSelectedItemPosition() == 0){
                            PistaImatge nuevaPistaI = new PistaImatge(ID.getText().toString(),nextID.getText().toString(),des.getText().toString(),Integer.parseInt(lat.getText().toString()),Integer.parseInt(lon.getText().toString()),"text");
                            ListaPistas.addPista(nuevaPistaI);
                            Toast.makeText(view.getContext(),"Image hint added properly",Toast.LENGTH_SHORT).show();
                        } else if(option.getSelectedItemPosition() == 1){
                            PistaAudio nuevaPistaA = new PistaAudio(ID.getText().toString(),nextID.getText().toString(),des.getText().toString(),Integer.parseInt(lat.getText().toString()),Integer.parseInt(lon.getText().toString()),"text");
                            ListaPistas.addPista(nuevaPistaA);
                            Toast.makeText(view.getContext(),"Audio hint added properly",Toast.LENGTH_SHORT).show();
                        } else if(option.getSelectedItemPosition() == 2){
                            PistaText nuevaPistaT = new PistaText(ID.getText().toString(),nextID.getText().toString(),des.getText().toString(),Integer.parseInt(lat.getText().toString()),Integer.parseInt(lon.getText().toString()),"text");
                            ListaPistas.addPista(nuevaPistaT);
                            Toast.makeText(view.getContext(),"Text hint added properly",Toast.LENGTH_SHORT).show();
                        }
                        setResult(RESULT_OK);
                        finish();
                    } else { //If the id already exist.
                        Toast.makeText(view.getContext(),"There's already a hint with that ID",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    //Go back button on the custom toolbar
    public void goBack(View v){
        Toast.makeText(this,"Canceled",Toast.LENGTH_SHORT).show();
        finish();
    }
}
