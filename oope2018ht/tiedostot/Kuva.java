package oope2018ht.tiedostot;

import oope2018ht.apulaiset.*;
/**
 *
 * @author Roni
 */
public class Kuva extends Tiedosto{
    /*
    * Attribuutit.
    *
    */
    
    /**
     * Kuvan leveys pikseleinä.
     */
    private int leveys;
    /**
     * Kuvan korkeus pikseleinä.
     */
    private int korkeus;
    
    /*
    * Rakentajat.
    *
    */
    
    public Kuva(String n, int koko, int l, int korkeus) throws IllegalArgumentException{
        super(n, koko);
        leveys(l);
        korkeus(korkeus);
    }
    
    /*
    * Aksessorit.
    *
    */
    @Getteri
    public int leveys(){
        return leveys;
    }
    @Setteri
    public void leveys(int l) throws IllegalArgumentException{
        if(l < 1)
            throw new IllegalArgumentException();
        else
            leveys = l;
    }
    @Getteri
    public int korkeus(){
        return korkeus;
    }
    @Setteri
    public void korkeus(int k) throws IllegalArgumentException{
        if(k < 1)
            throw new IllegalArgumentException();
        else
            korkeus = k;
    }
    
    /*
    * Object-luokan metodien korvaukset.
    *
    */
    
    public String toString(){
        return super.toString() + " " + leveys + "x" + korkeus;
    }
}
