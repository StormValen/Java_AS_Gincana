package com.gincana.enti.gincanaenti;
/**
 * Created by vgutierrez on 9/20/2017.
 */

public class PistaImatge extends Pista {
    private String path;

    public PistaImatge (){
        super("","","",0,0);
        this.path ="";
    }

    public PistaImatge(String codID, String nextCodID, String description, double latitude, double longitude,String path){
        super(codID,nextCodID,description,latitude,longitude);
        this.path =path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
        return (this.path);
    }

    public int getTipus(){
        return R.mipmap.ic_p_image;
    }
}
