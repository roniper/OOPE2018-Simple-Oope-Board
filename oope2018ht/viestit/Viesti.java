package oope2018ht.viestit;


import oope2018ht.apulaiset.*;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**
 *
 * @author Roni
 */
public class Viesti implements Comparable<Viesti>,Komennettava<Viesti>{
    /*
    * Attribuutit.
    *
    */
    /**
     * Viestin tunniste kokonaislukuna.
     */
    private int tunniste;
    /**
     * Viestin teksti merkkijonona.
     */
    private String viesti;
    /**
     * Lista vastauksista, jossa vastaukset tunnisteen mukaan nousevassa j�rjestyksess�.
     */
    private OmaLista vastaukset; 
    /**
     * Viite vastattuun viestiin
     */
    private Viesti vastaus;
    /**
     * Viestiin mahdollisesti lis�tt�v� tiedosto.
     */
    private Tiedosto tiedosto;
    
    /*
    * Rakentajat.
    *
    */
    
    public Viesti(int t, String v, Viesti vs, Tiedosto f) throws IllegalArgumentException{
        tunniste(t);
        viesti(v);
        vastaus(vs);
        tiedosto(f);
        vastaukset = new OmaLista();
    }
    
    /*
    * Aksessorit.
    *
    */
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
    public String viesti(){
        return viesti;
    }
    @Setteri
    public void viesti(String v) throws IllegalArgumentException{
        if(v == null || v.equals(""))
            throw new IllegalArgumentException();
        else
            viesti = v;
    }
    @Getteri
    public OmaLista vastaukset(){
        return vastaukset;
    }
    @Setteri
    public void vastaukset(OmaLista vst) throws IllegalArgumentException{
        if(vst == null)
            throw new IllegalArgumentException();
        else
            vastaukset = vst;
    }
    @Getteri
    public Viesti vastaus(){
        return vastaus;
    }
    @Setteri
    public void vastaus(Viesti vs){
        vastaus = vs;
    }
    @Getteri
    public Tiedosto tiedosto(){
        return tiedosto;
    }
    @Setteri
    public void tiedosto(Tiedosto f){
        tiedosto = f;
    }
    
    /*
    * Object-luokan metodien korvaukset.
    *
    */
    
    public String toString(){
        // Mik�li tiedosto l�ytyy
        if(tiedosto != null)
            return "#" + tunniste + " " + viesti + " (" + tiedosto + ")";
        else
            return "#" + tunniste + " " + viesti;
    }
    
    public boolean equals(Object o){
        try{
            // Asetetaan olioon Viesti-luokan viite,
            // jotta voidaan kutsua Viesti-Luokan aksessoreita
            Viesti v = (Viesti)o;
            
            // Muunnetaan nimet String-tyyppisiksi ennen vertailua
            String verrattava = Integer.toString(tunniste());
            String vertailtava = Integer.toString(v.tunniste());
            
            // Oliot ovat samat, jos attribuuttien arvot ovat samat
            return verrattava.equals(vertailtava);
        }
        catch(Exception e){
            return false;
        }
    }
    
    /*
    * Comparable-rajapinnan metodin toteutus.
    *
    */
    
    public int compareTo(Viesti o){
        Integer a = o.tunniste;
        Integer b = tunniste;
        return b.compareTo(a);
    }
    
    /*
    * Toteutetut metodit Komennettava-rajapinnasta.
    *
    */

    /** Hakee viesti� listalta, joka s�il�� viitteet viestiin vastaaviin viesteihin.
     * Hy�dynt�� OmaLista-luokan hae-operaatiota.
     *
     * @param haettava viite haettavaan viestiin.
     * @return viite l�ydettyyn tietoon. Palauttaa null-arvon, jos haettavaa
     * viesti� ei l�ydet�.
     * @throws IllegalArgumentException jos parametri on null-arvoinen.
     */
   public Viesti hae(Viesti haettava) throws IllegalArgumentException{
       // Jos haettava on null, heitet��n poikkeus.
       if(haettava != null){
           try{
               // Paluuarvona viite l�ydettyyn tietoon.
               return (Viesti)vastaukset.hae(haettava);
           }
           catch(Exception e){
               // Null, jos l�ydet��n poikkeus.
               return null;
           }
       }
       else
           throw new IllegalArgumentException();
   }
   
   /** Lis�� uuden viitteen listalle, joka s�il�� viitteet viestiin vastaaviin
     * viesteihin. Uusi viite lis�t��n listan loppuun. Viitett� ei lis�t�,
     * jos listalla on jo viite lis�tt�v��n vastaukseen. Hy�dynt�� hae-metodia.
     *
     * @param lisattava viite lis�tt�v��n viestiin.
     * @throws IllegalArgumentException jos lis�ys ep�onnistui, koska parametri
     * on null-arvoinen tai koska vastaus on jo lis�tty listalle.
     */
   public void lisaaVastaus(Viesti lisattava) throws IllegalArgumentException{
       // Tarkistetaan, ettei parametri ole null.
       if(lisattava == null)
           throw new IllegalArgumentException();
       else{
           // Tarkistetaan, ettei vastausta l�ydy jo listalta.
           if(hae(lisattava) != null && hae(lisattava).equals(lisattava))
               throw new IllegalArgumentException();
           else
               vastaukset.lisaa(lisattava);
       }
   }

   /** Asettaa viestin tekstiksi vakion POISTETTUTEKSTI ja poistaa viestiin
     * mahdollisesti liitetyn tiedoston asettamalla attribuutin arvoksi null-arvon.
     */
   public void tyhjenna(){
       // Asetetaan viestiksi Komennettava-rajapinnan tyhjennetyn viestin teksti.
       viesti(POISTETTUTEKSTI);
       // Asetetaan tiedosto nulliksi.
       tiedosto(null);
   }
}
