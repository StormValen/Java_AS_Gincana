package com.gincana.enti.gincanaenti;

/**
 * Created by vgutierrez on 9/20/2017.
 */

public class PistaText extends Pista {
    private String text;

    public PistaText (){
        super("","","",0,0);
        this.text="";
    }

    public PistaText(String codID, String nextCodID, String description, double latitude, double longitude,String text){
        super(codID,nextCodID,description,latitude,longitude);
        this.text=text;
    }

    public void setText(String text){
        this.text=text;
    }

    public String getText(){
        return (this.text);
    }

    public int getTipus(){
        return R.mipmap.ic_p_text;
    }
}
