package oope2018ht.viestit;

import oope2018ht.apulaiset.*;
import oope2018ht.omalista.OmaLista;

/**
 *
 * @author Roni
 */
public class Ketju implements Comparable<Ketju>{
    /*
    * Attribuutit.
    *
    */
    
    // Attribuutti ketjun oksaviestit sisältävän listan hallinnointiin
    private OmaLista viestiketju;
    
    private int tunniste;
    
    private String aihe;
    
    private boolean onkoAktiivinen;
    
    /*
    * Rakentajat.
    *
    */
    
    public Ketju(int t, String a) throws IllegalArgumentException{
        tunniste(t);
        aihe(a);
        viestiketju = new OmaLista();
    }
    
    /*
    * Aksessorit.
    *
    */
    @Getteri
    public OmaLista viestiketju(){
        return viestiketju;
    }
    @Setteri
    public void viestiketju(OmaLista vk) throws IllegalArgumentException{
        if(vk == null)
            throw new IllegalArgumentException();
        else
            viestiketju = vk;
    }
    @Getteri
    public int tunniste(){
        return tunniste;
    }
    @Setteri
    public void tunniste(int t) throws IllegalArgumentException{
        if(t < 1)
            throw new IllegalArgumentException();
        else
            tunniste = t;
    }
    @Getteri
    public String aihe(){
        return aihe;
    }
    @Setteri
    public void aihe(String a) throws IllegalArgumentException{
        if(a == null || a.equals(""))
            throw new IllegalArgumentException();
        else
            aihe = a;
    }
    @Getteri
    public boolean onkoAktiivinen(){
        return onkoAktiivinen;
    }
    @Setteri
    public void onkoAktiivinen(boolean onko){
        onkoAktiivinen = onko;
    }
    /*
    * Object-luokan metodien korvaukset.
    *
    */
    
    public String toString(){
        return "#" + tunniste + " " + aihe + " (" + viestiketju.koko() + " messages)";
    }
    
    /*
    * Comparable-rajapinnan metodin toteutus.
    *
    */

    public int compareTo(Ketju o){
        Integer a = o.tunniste;
        Integer b = tunniste;
        return b.compareTo(a);
    }
}
