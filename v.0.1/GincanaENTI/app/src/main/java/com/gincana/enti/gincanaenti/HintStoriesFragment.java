package com.gincana.enti.gincanaenti;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vgutierrez on 11/29/2017.
 */

public class HintStoriesFragment extends Fragment {
    private View vista;

    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState){
        vista = inflador.inflate(R.layout.storiesfragment, contenedor, false);
        return vista;
    }

    @Override
    public void onActivityCreated(Bundle state){
        super.onActivityCreated(state);

    }
}
