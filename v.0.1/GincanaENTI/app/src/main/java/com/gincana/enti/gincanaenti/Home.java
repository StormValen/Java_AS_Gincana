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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class Home extends AppCompatActivity{

   /* private GoogleApiClient apiClient;
    private GoogleMap mapa;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Set the default hints.
        PistaText testPistaText = new PistaText("0","1","Default text hint.",340.0,457.0,"text");
        ListaPistas.addPista(testPistaText);
        PistaAudio testPistaAudio = new PistaAudio("1","2","Default audio hint.",340.0,457.0,"path");
        ListaPistas.addPista(testPistaAudio);
        PistaImatge testPistaImagen = new PistaImatge("2","3","Default image hint.",340.0,457.0,"path");
        ListaPistas.addPista(testPistaImagen);

        Toolbar toolbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        updateCurrent(0);

        /*apiClient=new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addApi(LocationServices.API).build();
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.fragmentMaps);
        if(mapFragment==null){
            Toast.makeText(this,"Mapa no trobat",Toast.LENGTH_SHORT).show();
        }
        mapFragment.getMapAsync(this);*/
    }

    /*public void onMapReady(GoogleMap p){
        mapa = p;
    }

    public void onLocationChanged(Location location) {
    }

    public void onConnectionFailed(ConnectionResult result) {
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    }

    public void onConnectionSuspended(int i) {
    }

    public void onStatusChanged(String proveedor, int estado, Bundle extras) {
    }

    public void onProviderEnabled(String proveedor) {
    }

    public void onProviderDisabled(String proveedor) {
    }

    public void onConnected(@Nullable Bundle bundle) {
    }*/


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

    /*public void clickExit(View view){
        Toast.makeText(this,"Gracias por probar la aplicacion",Toast.LENGTH_SHORT).show();
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                finish();
                System.exit(0);
            }
        }.start();

    }*/
}
