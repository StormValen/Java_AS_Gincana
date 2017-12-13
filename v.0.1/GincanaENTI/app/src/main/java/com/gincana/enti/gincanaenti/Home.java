package com.gincana.enti.gincanaenti;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Principal;


public class Home extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener,NavigationView.OnNavigationItemSelectedListener{

    private GoogleApiClient apiClient;
    private LocationRequest mLocationRequest;
    private static final long INTERVAL = 1000; //1 segundo
    private static final long FASTEST_INTERVAL = 1000; // 1 segundo
    private static final float SMALLEST_DISPLACEMENT = 0.05F;
    final int requestCode=0;
    private String proveedor;
    private LocationManager manejador;

    private double locationLongitude;
    private double locationLatitude;

    private int PETICION_PERMISO_LOCALIZACION=0;


    LatLng currentPosition = new LatLng(0,0);

    private GoogleMap mMap;
    private Marker positionMarker;
    private boolean isTheSameHint = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Set the default hints.
        //PistaText testPistaText = new PistaText("0", "1", "Gran Via 2", 41.359287905385166, 2.129545211791992, "text");
        PistaText testPistaText = new PistaText("0", "1", "Home", 41.37431481670299, 2.108534127473831, "text");
        ListaPistas.addPista(testPistaText);
        PistaImatge testPistaImagen = new PistaImatge("1", "2", "Sagrada Familia", 41.40363195631695, 2.1743541955947876, "path");
        ListaPistas.addPista(testPistaImagen);
        PistaAudio testPistaAudio = new PistaAudio("2", "3", "Enti", 41.388074530341534, 2.1632257103919983, "path");
        ListaPistas.addPista(testPistaAudio);


        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        updateCurrent(0);


        //Necesary for the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Geolocalizacion
        apiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        // Objecte que permet tractar els canvis de posició
        manejador = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criterio = new Criteria();
        criterio.setCostAllowed(false);
        criterio.setAltitudeRequired(false);
        criterio.setAccuracy(Criteria.ACCURACY_FINE);
        proveedor = manejador.getBestProvider(criterio, true);
    }

    //Geolocalizacion
    @Override
    public void onStart()
    {
        super.onStart();

        /* Connexió al servidor de geolocalització */
        if (apiClient != null) {
            apiClient.connect();
        }

        /* Configuració de l'escoltador de posicions */
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT); //added
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        /* Si el permís està concedit, activar les escoltes de posició */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            manejador.requestLocationUpdates(proveedor, INTERVAL, SMALLEST_DISPLACEMENT, this);
        }
        else {  /* Si no està concedit, demanar-lo a l'usuari */
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("GPS")
                        .setMessage("Need to know your location")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
            }
        }
    }

    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.who){
            Intent i = new Intent(this, About.class);
            i.putExtra("Texto", ":)");
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Mètode que s'executa cada cop que es modifica la posició del dispositiu */
    public void onLocationChanged(Location location) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            if (lastLocation == null)
                Toast.makeText(this, "Can not connect", Toast.LENGTH_SHORT).show();
            else {
                /**** AQUEST MÈTODE ACTUALITZA LA INTERFICIE PER A MOSTRAR EN DOS TEXTVIEW LA LONGITUD I LATITUD ********/
                locationLatitude = lastLocation.getLatitude();
                locationLongitude = lastLocation.getLongitude();
                currentPosition = new LatLng(locationLatitude, locationLongitude);
                positionMarker.setPosition(currentPosition);
            }
        }
        Toast.makeText(this, "HOLI", Toast.LENGTH_SHORT).show();
        updateMyPosition();
        //if(!isTheSameHint){
            //checkHintPosition();   //OJO!!!!
            isTheSameHint = true;
        //}
    }

    /* Mètode que s'executa quan es produeix la connexió */
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            if (lastLocation==null)
                Toast.makeText(this, "Can not connect", Toast.LENGTH_SHORT).show();
            else
            {
                /**** AQUEST MÈTODE ACTUALITZA LA INTERFICIE PER A MOSTRAR EN DOS TEXTVIEW LA LONGITUD I LATITUD ********/
                locationLatitude = lastLocation.getLatitude();
                locationLongitude = lastLocation.getLongitude();
            }
        }
    }


    public void onProviderEnabled(String proveedor) {
        //Toast.makeText(this,"Proveidor habilitat: " + proveedor + "\n", Toast.LENGTH_LONG).show();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    }

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(ConnectionResult result) {
    }

    public void onStatusChanged(String proveedor, int estado, Bundle extras) {
    }

    public void onProviderDisabled(String proveedor) {
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
        //TextView actualHint = (TextView)findViewById(R.id.storiesActualHint);
        //actualHint.setText(text);
        ImageView imgTipoPista = (ImageView)findViewById((R.id.actualHintImage));
        imgTipoPista.setImageResource(R.mipmap.ic_personalizado);
    }

    public void updateCurrent(int position){
        //TextView actualHint = (TextView)findViewById(R.id.storiesActualHint);
        //actualHint.setText(ListaPistas.get(position).getDescription());
        ImageView imgTipoPista = (ImageView)findViewById((R.id.actualHintImage));
        imgTipoPista.setImageResource(ListaPistas.get(position).getTipus());
    }

    public void viewHint(View view){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(ListaPistas.get(0).getDescription());
            alertDialogBuilder.setMessage("Info");
            alertDialogBuilder.setIcon(R.mipmap.ic_hintimage);
            alertDialogBuilder.setPositiveButton("close",
                    new DialogInterface.OnClickListener() { //Ok.
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Delete the selected hint.

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


    }

    public void createMarker(){
        CameraPosition camPos = new CameraPosition.Builder()
                .target(currentPosition)
                .zoom(17)
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(camUpd3);
        positionMarker = mMap.addMarker(new MarkerOptions().position(currentPosition).title("My position").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_currentposition)));
    }

    public void updateMyPosition(){
        CameraPosition camPos = new CameraPosition.Builder()
                .target(currentPosition)
                .zoom(17)
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.animateCamera(camUpd3);
    }

    public void checkHintPosition(){
        updateMyPosition();

        //LatLng currentPosition = new LatLng(locationLatitude,locationLongitude);
        if(!ListaPistas.listaPistas.isEmpty()) {
            if (currentPosition.latitude - Float.valueOf(Double.toString(ListaPistas.get(0).getPosition().getLatitude())) < 0.005f && currentPosition.longitude - Float.valueOf(Double.toString(ListaPistas.get(0).getPosition().getLongitude())) < 0.005f) {
                LatLng pistaPostion = new LatLng(ListaPistas.get(0).getPosition().getLatitude(), ListaPistas.get(0).getPosition().getLongitude());
                mMap.addMarker(new MarkerOptions().position(pistaPostion).title(ListaPistas.get(0).getDescription()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_locationvisited)));
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Congratulations");
                alertDialogBuilder.setMessage("You unlocked a new hint!");
                alertDialogBuilder.setIcon(R.mipmap.ic_hintimage);
                alertDialogBuilder.setPositiveButton("close",
                        new DialogInterface.OnClickListener() { //Ok.
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //Delete the selected hint.


                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


                    if (!ListaPistas.listaPistas.isEmpty()) {
                        ListaPistas.deletePista(ListaPistas.get(0).getCodID());
                        updateCurrent(0);
                        isTheSameHint = false;
                    } else {
                        updateCurrent("EMPTY");
                    }




            }
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        createMarker();
        boolean success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        Home.this, R.raw.maps_black));
    }
}
