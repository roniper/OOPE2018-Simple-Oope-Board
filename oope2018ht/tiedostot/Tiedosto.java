package oope2018ht.tiedostot;

import oope2018ht.apulaiset.*;
/**
 *
 * @author Roni
 */
public abstract class Tiedosto{
    /*
    * Attribuutit.
    *
    */
    
    /**
     * Tiedoston nimi merkkijonona.
     */
    private String nimi;
    /**
     * Tiedoston koko tavuina.
     */
    private int koko;
    
    /*
    * Rakentajat.
    *
    */
    
    public Tiedosto(String n, int k) throws IllegalArgumentException{
        nimi(n);
        koko(k);
    }

    
    /*
    * Aksessorit.
    *
    */
    @Getteri
    public String nimi(){
        return nimi;
    }
    @Setteri
    public void nimi(String n) throws IllegalArgumentException{
        if(n == null || n.equals(""))
            throw new IllegalArgumentException();
        else
            nimi = n;
    }
    @Getteri
    public int koko(){
        return koko;
    }
    @Setteri
    public void koko(int k) throws IllegalArgumentException{
        if(k < 1)
            throw new IllegalArgumentException();
        else
            koko = k;
    }
    
    /*
    * Object-luokan metodien korvaukset.
    *
    */
    
    public String toString(){
        return nimi + " " + koko + " B";
    }
}
