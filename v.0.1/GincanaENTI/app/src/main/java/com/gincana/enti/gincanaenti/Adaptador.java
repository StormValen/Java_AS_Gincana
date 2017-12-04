package com.gincana.enti.gincanaenti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    protected LayoutInflater inflador;
    protected Context contexto;
    protected View.OnLongClickListener onLongClickListener;

    public Adaptador(Context contexto) {
        this.contexto = contexto;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Variables of every item on the list.
        public TextView nomVersio;
        public TextView desPista;
        public ImageView imgTipoPista;
        public TextView Latitud;
        public TextView Longitud;
        public TextView NextID;

        public ViewHolder(View itemView) {
            super(itemView);
            //Properties of every item on the list -> 'elemento_pista'.
            nomVersio=(TextView)itemView.findViewById(R.id.nomVersio);
            desPista=(TextView)itemView.findViewById(R.id.desPista);
            imgTipoPista=(ImageView)itemView.findViewById(R.id.imageView2);
            Latitud=(TextView)itemView.findViewById(R.id.Latitud);
            Longitud=(TextView)itemView.findViewById(R.id.Longitud);
            NextID =(TextView)itemView.findViewById(R.id.nextID);
        }
    }
    //Mètode obligatori que genera un ViewHolder a partir de l'id de l'XML list_item
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.elemento_pista, null);
        v.setOnLongClickListener(onLongClickListener);
        return new ViewHolder(v);
    }
    // Mètode obligatori que ens permet especificar què posar als views de cada item
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicio) {

        holder.nomVersio.setText("ID: " + ListaPistas.get(posicio).getCodID());
        holder.desPista.setText(ListaPistas.get(posicio).getDescription());
        holder.Latitud.setText("Lat: " + Double.toString(ListaPistas.get(posicio).getPosition().getLatitude()));
        holder.Longitud.setText("Lon: " + Double.toString(ListaPistas.get(posicio).getPosition().getLongitude()));
        holder.NextID.setText("Next ID: " + ListaPistas.get(posicio).getNextCodID());

        int imageTipus = ListaPistas.get(posicio).getTipus();
        holder.imgTipoPista.setImageResource(imageTipus);
    }
    //Mètode obligatori que retorna el número d'elements total de la llista
    @Override public int getItemCount() {
        return  ListaPistas.listaPistas.size();
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener){
        this.onLongClickListener = onLongClickListener;
    }
}
