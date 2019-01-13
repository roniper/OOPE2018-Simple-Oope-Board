package oope2018ht.tiedostot;

import oope2018ht.apulaiset.*;
/**
 *
 * @author Roni
 */
public class Video extends Tiedosto{
    /*
    * Attribuutit.
    *
    */
    
    /**
     * Videon pituus sekunteina.
     */
    private double pituus;
    
    /*
    * Rakentajat.
    *
    */
    
    public Video(String n, int k, double p) throws IllegalArgumentException{
        super(n, k);
        pituus(p);
    }
    
    /*
    * Aksessorit.
    *
    */

    @Getteri
    public double pituus(){
        return pituus;
    }
    @Setteri
    public void pituus(double p) throws IllegalArgumentException{
        if(p <= 0)
            throw new IllegalArgumentException();
        else
            pituus = p;
    }
    
    /*
    * Object-luokan metodien korvaukset.
    *
    */
    
    public String toString(){
        return super.toString() + " " + pituus + " s";
    }
}
