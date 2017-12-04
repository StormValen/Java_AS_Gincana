package com.gincana.enti.gincanaenti;
/**
 * Created by vgutierrez on 9/20/2017.
 */

//Alt+Intro para hacer el import.

public abstract class Pista {
    private String codID, nextCodID;
    private String description;
    private Coord position;

    public Pista(){
        this.codID="";
        this.nextCodID="";
        this.description="";
        this.position= new Coord(0,0);

    }

    public Pista(String codID, String nextCodID, String description, double latitude, double longitude){
        this.codID=codID;
        this.nextCodID=nextCodID;
        this.description=description;
        this.position=new Coord(latitude, longitude );
    }

    public Pista(String codID, String nextCodID, String description, Coord position){
        this.codID=codID;
        this.nextCodID=nextCodID;
        this.description=description;
        this.position=position;
    }

    public void setCodID(String codID){
        this.codID=codID;
    }

    public void setNextCodID(String nextCodID){
        this.nextCodID=nextCodID;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public void position(double latitude, double longitude){
        this.position=new Coord(latitude, longitude );
    }

    public String getCodID(){
        return (this.codID);
    }

    public String getNextCodID(){
        return (this.nextCodID);
    }

    public String getDescription(){
        return (this.description);
    }

    public Coord getPosition(){
        return (this.position);
    }

    public abstract int getTipus();
}
