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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        //Set custom toolbar name.
        TextView name = (TextView)findViewById(R.id.appbar_name);
        name.setText("About");
        //Print text send from the principal.java.
        Bundle extras = getIntent().getExtras();
        String text = extras.getString("Texto");
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
        //Floating button  that shown e-mail.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "valentingutierrezlosada@enti.cat", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //Go back button on the custom toolbar.
    public void goBack(View v){finish();}
}
