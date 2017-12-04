package com.gincana.enti.gincanaenti;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by vgutierrez on 9/20/2017.
 */

public class ListaPistas {
    public static ArrayList<Pista>listaPistas = new ArrayList<Pista>();

    public ListaPistas(){

    }

    public static void addPista(Pista p){
            listaPistas.add(p);
    }

    public static Pista searchPista(String id){
        Pista p = null;
        Iterator<Pista> i = listaPistas.iterator(); //puntero
        boolean trobat= false;
        while(i.hasNext() && trobat==false){
            p = i.next();
            if(p.getCodID().equals(id)){
                trobat = true;
                return p;
            }
        }
        return null;
    }

    public static void deletePista(String id){
        Pista p = null;
        Iterator<Pista> i = listaPistas.iterator(); //puntero
        boolean trobat = false;
        while(i.hasNext() && trobat==false){
            p = i.next();
            if(p.getCodID()== id){
                trobat = true;
                listaPistas.remove(p);
            }
        }
    }

    public static Pista nextPista(String id){
        Pista p = null;
        Iterator<Pista> i = listaPistas.iterator(); //puntero
        boolean trobat = false;
        while(i.hasNext() && trobat==false){
            p = i.next();
            if(p.getCodID() == id){
                trobat = true;
            }
        }
        if(trobat == true){
            return p;
        }else{
            return null;
        }
    }

    public static int size(){return listaPistas.size();}
    public static Pista get(int index){return listaPistas.get(index);}
}
