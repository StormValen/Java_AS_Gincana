package com.gincana.enti.gincanaenti;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;


public class Home extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Set the default hints.
        PistaText testPistaText = new PistaText("0", "1", "Default text hint.", 340.0, 457.0, "text");
        ListaPistas.addPista(testPistaText);
        PistaAudio testPistaAudio = new PistaAudio("1", "2", "Default audio hint.", 340.0, 457.0, "path");
        ListaPistas.addPista(testPistaAudio);
        PistaImatge testPistaImagen = new PistaImatge("2", "3", "Default image hint.", 340.0, 457.0, "path");
        ListaPistas.addPista(testPistaImagen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        updateCurrent(0);

        //Necesary for the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);
    }

    //Basic toolbar menu.
    @Override public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.testmenu,menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //Manage Hints
        if(id == R.id.optionGestionarPistas){
            Intent i = new Intent(this,Hints_Management.class);
            startActivityForResult(i,0);
            return true;
        }
        //About
        if(id == R.id.optionInformacion) {
            Intent i = new Intent(this, About.class);
            i.putExtra("Texto", ":)");
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 0 && resultCode == RESULT_OK &&!ListaPistas.listaPistas.isEmpty()){
            updateCurrent(0);
        }else{
            updateCurrent("EMPTY");
        }
    }

    public void updateCurrent(String text){
        TextView actualHint = (TextView)findViewById(R.id.storiesActualHint);
        actualHint.setText(text);
        ImageView imgTipoPista = (ImageView)findViewById((R.id.actualHintImage));
        imgTipoPista.setImageResource(R.mipmap.ic_personalizado);
    }

    public void updateCurrent(int position){
        TextView actualHint = (TextView)findViewById(R.id.storiesActualHint);
        actualHint.setText(ListaPistas.get(position).getDescription());
        ImageView imgTipoPista = (ImageView)findViewById((R.id.actualHintImage));
        imgTipoPista.setImageResource(ListaPistas.get(position).getTipus());
    }

    public void viewHint(View view){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(ListaPistas.get(0).getDescription());
            alertDialogBuilder.setMessage("Info");
            alertDialogBuilder.setIcon(ListaPistas.get(0).getTipus());
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() { //Ok.
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Delete the selected hint.

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng barcelona = new LatLng(41.390205, 2.154007);


        mMap.addMarker(new MarkerOptions().position(barcelona).title("Marker in Barcelona").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_personalizado)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(barcelona));
        boolean success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        Home.this, R.raw.maps_black));
    }
}
