package com.gincana.enti.gincanaenti;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Hints_Management extends AppCompatActivity implements View.OnLongClickListener {

    private String selectedHintToDelete;
    private RecyclerView recyclerView;
    public Adaptador adaptador;
    private RecyclerView.LayoutManager layoutManager;
    private CardView FondoPistaSeleccionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_pistas);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_gestionpistas);
        adaptador = new Adaptador(this); //PREGUNTAR POR ESTO.
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(this); //PREGUNTAR POR ESTO.
        recyclerView.setLayoutManager(layoutManager);

        adaptador.setOnLongClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        //Set custom toolbar name.
        TextView name = (TextView)findViewById(R.id.appbar_name);
        name.setText("Hints management");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FondoPistaSeleccionada != null){ //Unselect the previously selected hint.
                    FondoPistaSeleccionada.setCardBackgroundColor(Color.parseColor("#FF1D1A21"));
                    selectedHintToDelete = "";
                }
                //Start 'Add hint' activity.
                startActivityForResult(new Intent(view.getContext(),Add_Hint.class),0);
            }
        });
    }

    //Custom bin button on the toolbar.
    @Override public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.delete,menu);
        return true;
    }

    //Returning from 'Add hint' activity.
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 0 && resultCode == RESULT_OK){ adaptador.notifyDataSetChanged(); }
    }

    //Long press on a hint in the recycler view.
    public boolean onLongClick(View v){
        //Unselect the previously selected hint.
        if(FondoPistaSeleccionada != null){ FondoPistaSeleccionada.setCardBackgroundColor(Color.parseColor("#FF1D1A21")); }
        //Selected hint on the recycler view
        Pista pToRemove = ListaPistas.get(recyclerView.getChildAdapterPosition(v));
        //Set the card background on green.
        FondoPistaSeleccionada = (CardView)v.findViewById(R.id.cardViewPista);
        FondoPistaSeleccionada.setCardBackgroundColor(Color.parseColor("#FF236F31"));
        //Get the ID from the  hint to delete.
        selectedHintToDelete = pToRemove.getCodID();
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //Create a popup if the bin is there's a hint selected and the bin is clicked.
        if(id == R.id.delete && selectedHintToDelete != "" && FondoPistaSeleccionada != null){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Delete item permanently?");
            alertDialogBuilder.setMessage("This action can't be undone");
            alertDialogBuilder.setIcon(R.mipmap.ic_warning);
            alertDialogBuilder.setPositiveButton("yes, delete it",
                    new DialogInterface.OnClickListener() { //Ok.
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Delete the selected hint.
                            ListaPistas.deletePista(selectedHintToDelete);
                            selectedHintToDelete = "";
                            FondoPistaSeleccionada.setCardBackgroundColor(Color.parseColor("#FF1D1A21"));
                            adaptador.notifyDataSetChanged();
                        }
                    });
            alertDialogBuilder.setNegativeButton("no",
                    new DialogInterface.OnClickListener() { //Cancel.
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Undo the selection movement.
                            selectedHintToDelete = "";
                            FondoPistaSeleccionada.setCardBackgroundColor(Color.parseColor("#FF1D1A21"));
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            adaptador.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Go back button on the custom toolbar.
    public void goBack(View view){
        Intent i = new Intent();
        setResult(RESULT_OK,i);
        finish();
    }
}
