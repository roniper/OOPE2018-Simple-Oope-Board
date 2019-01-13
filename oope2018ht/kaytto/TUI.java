package oope2018ht.kaytto;

import java.io.*;
import oope2018ht.apulaiset.*;
import oope2018ht.viestit.*;

/**
 *
 * @author Roni
 */
public class TUI {
    
    /*
    * Rakentajat.
    *
    */
    public TUI(){
        toiminta();
    }
    
    public void toiminta(){
        // Vakioita
        final String LUOKETJU = "add ";
        final String KETJUT = "catalog";
        final String VALITSE = "select";
        final String LUOVIESTI = "new";
        final String VASTAA = "reply";
        final String PUU = "tree";
        final String LISTAA = "list";
        final String PAA = "head";
        final String HANTA = "tail";
        final String TYHJENNA = "empty";
        final String HAEVIESTI = "find";
        final String LOPETA = "exit";
        final String VIRHE = "Error!";
        
        // Muuttujia
        boolean jatketaan = true;
        String syote = "";
        String viesti = "";
        String tiedostoNimi = "";
        String rivi = "";
        // Tiedostosta luetut tiedot
        String tiedot = "";
        
        // Luodaan uusi alue-olio
        Alue uusiAlue = new Alue();
        
        // Ohjelman käynnistyessä tulostetaan näytölle tervehdysteksti
        System.out.println("Welcome to S.O.B.");
        
        do{
            // Tulostetaan komentokehotteen merkki
            System.out.print(">");
            
            // Luetaan käyttäjän syöte
            String lue = In.readString();
            
            // Pätkitään käyttäjän syöte
            String[] jono = lue.split("[ ]");
            
            // Otetaan parametrinä saatu syöte merkkijonoksi.
            for(int i = 1; i < jono.length; i++){
                if(i == 1){
                    syote = jono[i];
                }
                else{
                    syote += " " + jono[i];
                }
            }
            
            // Etsitään käyttäjän syötteestä tiedostoa
            // ja otetaan sellaisen löydyttyä nimi talteen
            // ja poistetaan tiedosto-parametri pois syötteestä.
            for(int i = 0; i < jono.length; i++){
                if(jono[i].startsWith("&")){
                    tiedostoNimi = jono[i];
                    tiedostoNimi = tiedostoNimi.replace("&","");
                    // Syöte ilman komentoa ja tiedostoa, eli pelkkä viesti
                    for(int j = 1; j < jono.length-1; j++){
                        if(j == 1){
                            syote = jono[j];
                        }
                        else
                            syote += " " + jono[j];
                    }
                }
            }
            

            // Viestiketjun luominen
            if(lue.startsWith(LUOKETJU) && jono.length > 1){
                if(uusiAlue.luoKetju(syote) == true){
                    jatketaan = true;
                }
                else
                    System.out.println(VIRHE);
            }
            // Viestiketjujen listaaminen
            else if(lue.equals(KETJUT)){
                uusiAlue.listaaKetjut();
            }
            // Viestiketjun valinta
            else if(lue.startsWith(VALITSE) && jono.length == 2){
                try{
                    int ketjunTunniste = Integer.parseInt(jono[1]);
                    if(ketjunTunniste > 0 && uusiAlue.valitseKetju(ketjunTunniste) == true)
                        jatketaan = true;
                    else
                        System.out.println(VIRHE);
                }
                catch(NumberFormatException e){
                    System.out.println(VIRHE);
                }
            }
            // Uuden viestin luominen
            else if(lue.startsWith(LUOVIESTI) && jono.length > 1){
                if(!tiedostoNimi.equals("")){
                    try {
                        tiedot = uusiAlue.lueTiedosto(tiedostoNimi);
                        if(uusiAlue.luoViesti(syote, tiedot) == true){
                            jatketaan = true;
                        }
                        else
                            System.out.println(VIRHE);
                    } catch (FileNotFoundException ex) {
                        System.out.println(VIRHE);
                    }
                }
                else{
                    if(uusiAlue.luoViesti(syote, null) == true){
                        jatketaan = true;
                    }
                    else{
                        System.out.println(VIRHE);
                    }
                }
            }
            // Viestiin vastaaminen
            else if(lue.startsWith(VASTAA) && jono.length > 2){
                if(!tiedostoNimi.equals("")){
                    try {
                        tiedot = uusiAlue.lueTiedosto(tiedostoNimi);
                        // Erotellaan vastattavan viestin tunniste syötteestä
                        if(uusiAlue.vastaaViestiin(syote, tiedot) == true){
                            jatketaan = true;
                        }
                        else
                            System.out.println(VIRHE);
                    } catch (FileNotFoundException ex) {
                        System.out.println(VIRHE);
                    }
                }
                else{
                    if(uusiAlue.vastaaViestiin(syote, null) == true){
                        jatketaan = true;
                    }
                    else{
                        System.out.println(VIRHE);
                    }
                }
            }
            // Ketjun tulostus puumuodossa
            else if(lue.equals(PUU)){
                uusiAlue.tulostaPuuna();
            }
            // Ketjun tulostus listana
            else if(lue.equals(LISTAA)){
                uusiAlue.listaaViestit();
            }
            // Vanhimpien viestien listaaminen
            else if(lue.startsWith(PAA) && jono.length == 2){
                // Tarkastetaan, että käyttäjän antama pituus on integer
                try{
                    // Otetaan parametrina annettu haluttujen viestien lukumäärä
                    int lkm = Integer.parseInt(jono[1]);
                    if(lkm > 0 && uusiAlue.haeVanhimpia(lkm))
                        jatketaan = true;
                    else
                        System.out.println(VIRHE);
                }
                catch(NumberFormatException e){
                    System.out.println(VIRHE);
                }
            }
            // Uusimpien viestien listaaminen
            else if(lue.startsWith(HANTA) && jono.length == 2){
                // Tarkastetaan, että käyttäjän antama pituus on integer
                try{
                    // Otetaan parametrina annettu haluttujen viestien lukumäärä
                    int lkm = Integer.parseInt(jono[1]);
                    if(lkm > 0 && uusiAlue.haeUusimpia(lkm))
                        jatketaan = true;
                    else
                        System.out.println(VIRHE);
                }
                catch(NumberFormatException e){
                    System.out.println(VIRHE);
                }
            }
            // Viestin tyhjentäminen
            else if(lue.startsWith(TYHJENNA) && jono.length == 2){
                int tyhjennettava = Integer.parseInt(jono[1]);
                uusiAlue.tyhjennaViesti(tyhjennettava);
            }
            // Tekstin hakeminen
            else if(lue.startsWith(HAEVIESTI + " ")){
                // Koska käyttäjälle annetaan mahdollisuus hakea myös yhdestä
                // tai useammasta välilyönnistä koostuvaa merkkijonoa,
                // täytyy käyttäjän syötettä muokata hieman erilailla kuin aiemmin.
                // Otetaan käyttäjän syöte ja poistetaan siitä merkkijono "find ",
                // jolloin voidaan erotella vain käyttäjän haluama haku, 
                // joka voi koostua myös välilyönneistä.
                syote = lue.replace("find ", "");
                // Tämän jälkeen kutsutaan merkkijonolla viestejä hakevaa metodia
                uusiAlue.haeTeksti(syote);
            }
            // Ohjelman lopetus
            else if(lue.equals(LOPETA)){
                jatketaan = false;
                System.out.println("Bye! See you soon.");
            }
            else{
                System.out.println(VIRHE);
            }
            // Tyhjennetään mahdollinen tiedosto, ettei se jää kaikkiin tuleviin lisäyksiin
            tiedostoNimi = "";
        }
        while(jatketaan);
    }
}
