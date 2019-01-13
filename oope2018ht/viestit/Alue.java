package oope2018ht.viestit;

import java.io.*;
import oope2018ht.apulaiset.*;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.*;

/**
 *
 * @author Roni
 */
public class Alue {
    /*
    * Attribuutit.
    *
    */
    
    // Attribuutti, jolta viitteet alueen viestiketjuille
    private OmaLista ketjut;
    
    // Ketju jota hallinnoidaan. Pidetään yllä aktiivinen-attribuutin avulla
    private Ketju nykyinen;
    
    // Apumuuttuja, jota käytetään aktiivisen viestiketjun vaihtamisessa
    private Ketju oliNykyinen;
    
    // Juoksevat kokonaisuluvut viestin ja ketjun tunnisteille
    int viestiTunniste = 1;
    int ketjuTunniste = 1;
    
    /*
    * Rakentajat.
    *
    */
    
    public Alue(){
        ketjut = new OmaLista();
    }
    
    /*
    * Aksessorit.
    *
    */
    @Getteri
    public OmaLista ketjut(){
        return ketjut;
    }
    @Setteri
    public void ketjut(OmaLista k){
        ketjut = k;
    }
    @Getteri
    public Ketju nykyinen(){
        return nykyinen;
    }
    @Setteri
    public void nykyinen(Ketju n){
        nykyinen = n;
    }
    @Getteri
    public Ketju oliNykyinen(){
        return oliNykyinen;
    }
    @Setteri
    public void oliNykyinen(Ketju n){
        oliNykyinen = n;
    }
    
    // Operaatio mahdollisen tiedoston lukemiselle
    public String lueTiedosto(String f) throws FileNotFoundException{
        try{
            // Tiedostopolku
            //String file = "./src/" + f;
            // Avataan, luetaan, lasketaan rivit ja suljetaan tiedosto
            FileInputStream syotevirta = new FileInputStream(f);
            InputStreamReader lukija = new InputStreamReader(syotevirta);
            BufferedReader puskuroituLukija = new BufferedReader(lukija);
            
            // Palautetaan myös tiedoston nimi merkkijonossa, että voidaan käyttää
            // sitä kuva- tai videotiedoston luomisessa myöhemmin
            String rivi = f + " ";
            while(puskuroituLukija.ready()){
                rivi += puskuroituLukija.readLine();
            }
            return rivi;
        }
        catch(IOException e){
            throw new FileNotFoundException();
        }
    }
    
    // Operaatio uuden viestiketjun luomiselle
    public boolean luoKetju(String aihe){
        if(aihe != null){
            try{
                // Mikäli viestiketju on ensimmäinen, joka alueelle lisätään,
                // tehdään siitä aktiivinen viestiketju automaattisesti.
                if(ketjut.onkoTyhja()){
                    Ketju uusiKetju = new Ketju(ketjuTunniste, aihe);
                    uusiKetju.onkoAktiivinen(true);
                    nykyinen = uusiKetju;
                    boolean lisays = ketjut.lisaa(uusiKetju);
                    ketjuTunniste += 1;
                    return lisays;
                }
                else{
                    Ketju uusiKetju = new Ketju(ketjuTunniste, aihe);
                    uusiKetju.onkoAktiivinen(false);
                    ketjuTunniste += 1;
                    return ketjut.lisaa(uusiKetju);
                }
                    
            }
            catch(IllegalArgumentException e){
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    // Operaatio viestiketjujen listaamiselle
    public void listaaKetjut() {
        for(int i = 0; i < ketjut.koko(); i++){
            System.out.println(ketjut.alkio(i));
        }
    }
    
    // Operaatio aktiivisen ketjun valinnalle
    public boolean valitseKetju(int tunniste) {
        // Tarkastetaan, että tunnisteen luku on pienempi tai yhtä suuri
        // kuin ketjulistan koko
        if(tunniste <= ketjut.koko()){
            // Otetaan entinen aktiivinen säilöön ja muutetaan sen totuusarvo
            oliNykyinen = nykyinen;
            oliNykyinen.onkoAktiivinen(false);
            // Käydään viestiketju listaa läpi, ja etsitään aktiiviseksi vaihdettava ketju
            for(int i = 0; i < ketjut.koko(); i++){
                if(i == tunniste - 1){
                    nykyinen = (Ketju)ketjut.alkio(i);
                    nykyinen.onkoAktiivinen(true);
                }
            }
            return true;
        }
        else
            return false;
    }
    
    // Operaatio uuden viestin luomiselle, joka ei ole vastaus mihinkään
    public boolean luoViesti(String syote, String tiedot) {
        if(!ketjut.onkoTyhja()){
            if(syote != null && tiedot == null){
                 Viesti uusiViesti = new Viesti(viestiTunniste, syote, null, null);
                 OmaLista lisays = nykyinen.viestiketju();
                 viestiTunniste += 1;
                 return lisays.lisaa(uusiViesti);
            }
            else if(syote != null){
                Tiedosto muutettu = muutaTiedot(tiedot);
                Viesti uusiViesti = new Viesti(viestiTunniste, syote, null, muutettu);
                OmaLista lisays = nykyinen.viestiketju();
                viestiTunniste += 1;
                return lisays.lisaa(uusiViesti);
            }
            else
                return false;
        }
        else
            return false;
    }
    
    // Operaatio jolla muutetaan tiedostosta saatu String Kuva-
    // tai Video-luokan käytettävään muotoon
    public Tiedosto muutaTiedot(String tiedot){
        // Pätkitään saatu tieto String.
        // Jonossa ensimmäisenä siihen manuaalisesti lisätty tiedoston nimi,
        // jonka jälkeen tiedoston sisältö
        String[] jono = tiedot.split("[ ]");
        
        int koko = Integer.parseInt(jono[2]); 
        
        // Tarkastetaan onko tiedosto kuva- vai videotiedosto
        if(jono[1].equals("Kuva")){
            // Otetaan kuvalle ominaiset tiedot ylös
            int leveys = Integer.parseInt(jono[3]); 
            int korkeus = Integer.parseInt(jono[4]);
            Kuva luotuKuva = new Kuva(jono[0], koko, leveys, korkeus);
            return luotuKuva;
        }
        else if(jono[1].equals("Video")){
            // Otetaan videolle ominaiset tiedot ylös
            double pituus = Double.parseDouble(jono[3]);
            Video luotuVideo = new Video(jono[0], koko, pituus);
            return luotuVideo;
        }
        else
            return null;
    }
    
    // Operaatio jolla lisätään viestille vastaus
    public boolean vastaaViestiin(String syote, String tiedot){
        // Virhe, mikäli ei viestiketjuja tai aktiivisessa ketjussa ei ole viestejä
        if(!ketjut.onkoTyhja() && !nykyinen.viestiketju().onkoTyhja()){
            // Otetaan syötteestä erilleen viestin tunniste, johon vastataan
            String[] jono = syote.split("[ ]");
            String t = jono[0];
            // Muunnetaan se integeriksi
            int vastattavanTunniste = Integer.parseInt(t);
            // Otetaan tunniste pois syötteestä viestin kirjoittamista varten
            String poistettava = t + " ";
            syote = syote.replace(poistettava, "");
            // Tarkistetaan onko parametrit annettu oikein
            if(vastattavanTunniste == (int)vastattavanTunniste){
                if(syote != null && tiedot == null){
                    // Haetaan viite viestiin, johon vastataan
                    int i = 0;
                    while(i < vastattavanTunniste){
                        // Käydään nykyisen viestiketjun alkioita läpi ja vertaillaan tunnisteita
                        Viesti vastattava = (Viesti)nykyinen.viestiketju().alkio(i);
                        // Mikäli löytyy viesti, jolle halutaan vastaus, lisätään se
                        if(vastattava.tunniste() == vastattavanTunniste){
                            Viesti uusiVastaus = new Viesti(viestiTunniste, syote, vastattava, null);
                            vastattava.lisaaVastaus(uusiVastaus);
                            OmaLista lisays = nykyinen.viestiketju();
                            viestiTunniste += 1;
                            return lisays.lisaa(uusiVastaus);
                        }
                        else
                            i++;
                    }
                    return false;
                }
                else if(syote != null){
                    Tiedosto muutettu = muutaTiedot(tiedot);
                    // Haetaan viite viestiin, johon vastataan
                    int i = 0;
                    while(i < vastattavanTunniste){
                        // Käydään nykyisen viestiketjun alkioita läpi ja vertaillaan tunnisteita
                        Viesti vastattava = (Viesti)nykyinen.viestiketju().alkio(i);
                        // Mikäli löytyy viesti, jolle halutaan vastaus, lisätään se
                        if(vastattava.tunniste() == vastattavanTunniste){
                            Viesti uusiVastaus = new Viesti(viestiTunniste, syote, vastattava, muutettu);
                            vastattava.lisaaVastaus(uusiVastaus);
                            OmaLista lisays = nykyinen.viestiketju();
                            viestiTunniste += 1;
                            return lisays.lisaa(uusiVastaus);
                        }
                        else
                            i++;
                    }
                    return false;
                }
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
    }
    
    // Operaatio joka luo ketjujen tulostuksille alun
    private void tulosteAlku() {
        System.out.println("=\n== " + nykyinen + "\n===");
    }
    
    // Operaatio aktiivisen vistiketjun viestien tulostamiseen puumuodossa (parametriton)
    public void tulostaPuuna(){
        // Tulostetaan listauksen alkuun määritelty muotoilu
        tulosteAlku();
        int i = 0;
        // Käydään läpi nykyisen viestiketjun viestit
        while(i < nykyinen.viestiketju().koko()){
            // Otetaan nykyisessä listan paikassa oleva viesti talteen
            Viesti tulostettava = (Viesti)nykyinen.viestiketju().alkio(i);
            // Tarkastetaan onko viesti vastaus toiseen viestiin vai ei.
            // Mikäli viesti ei ole vastaus mihinkään, käydään läpi sen mahdolliset
            // vastaukset.
            if(tulostettava.vastaus() == null){
                tulostaPuuna(tulostettava, 0);
                i++;
            }
            // Jos viesti on kuitenkin vastaus toiseen viestiin, ei tulosteta mitään,
            // vaan jatketaan iterointia ketjun viestien läpi
            else
                i++;
        }
    }
    
    // Operaatio aktiivisen vistiketjun viestien tulostamiseen puumuodossa (parametrillinen)
    public void tulostaPuuna(Viesti viesti, int syvyys){
        // Alustetaan sisennys
        String sisennys = "";
        // Asetetaan sisennys. Mikäli ei olla vielä vastauksissa, ei sisennetä
        if(syvyys > 0){
            for(int i = 0; i < syvyys; i++){
                // Viestin vastaus sisennetään kolmella välilyönnillä
                sisennys += "   ";
            }
        }
        // Asetetaan apuviite viestin vastaukset säilövään listaan
        OmaLista vastaukset = viesti.vastaukset();
        // Tulostetaan viestin merkkijonoesitys
        System.out.println(sisennys + viesti);
        // Tulostetaan vastaukset rekursiivisesti. Metodista palataan,
        // kun vastauslista on tyhjä
        int j = 0;
        while(j < vastaukset.koko()){
            tulostaPuuna((Viesti)vastaukset.alkio(j), syvyys + 1);
            j++;
        }
    }
    
    // Operaatio aktiivisen viestiketjun viestien tulostamiseen listana
    public void listaaViestit(){
        // Tulostetaan listauksen alkuun määritelty muotoilu
        tulosteAlku();
        // Tulostetaan ketjun sisältö
        for(int i = 0; i < nykyinen.viestiketju().koko(); i++){
            System.out.println(nykyinen.viestiketju().alkio(i));
        }
    }
    
    // Operaatio vanhimpien viestien listaamiselle
    public boolean haeVanhimpia(int lkm){
        // Tarkastetaan että listalla on jotain ja parametrinä annettu lkm
        // on pienempi kuin olemassa olevan listan koko
        if(!nykyinen.viestiketju().onkoTyhja() && nykyinen.viestiketju().koko() >= lkm){
            // Hyödynntetään OmaLista-luokan annaAlku-metodia
            OmaLista alku = nykyinen.viestiketju().annaAlku(lkm);
            for(int i = 0; i < alku.koko(); i++){
                System.out.println(alku.alkio(i));
            }
            return true;
        }
        else
            return false;
    }
    
    // Operaatio uusimpien viestien listaamiselle
    public boolean haeUusimpia(int lkm){
        // Tarkastetaan että listalla on jotain ja parametrinä annettu lkm
        // on pienempi kuin olemassa olevan listan koko
        if(!nykyinen.viestiketju().onkoTyhja() && nykyinen.viestiketju().koko() >= lkm){
            // Hyödynntetään OmaLista-luokan anna-metodia
            OmaLista loppu = nykyinen.viestiketju().annaLoppu(lkm);
            for(int i = 0; i < loppu.koko(); i++){
                System.out.println(loppu.alkio(i));
            }
            return true;
        }
        else
            return false;
    }
    
    // Operaatio viestin tyhjentämiselle
    public void tyhjennaViesti(int tunniste){
        // Haetaan tyhjennettävä viesti
        Viesti haettu = (Viesti)nykyinen.viestiketju().alkio(tunniste-1);
        // Suoritetaan Komennettava-rajapinnasta peritty tyhjennys
        // joka korvaa viestin sisällön yhdysmerkeillä ja poistaa mahdollisen tiedoston
        haettu.tyhjenna();
    }
    
    // Operaatio tekstin hakemiselle viesteistä
    public void haeTeksti(String syote){
        // Käydään läpi nykyisen viestiketjun viestit
        for(int i = 0; i < nykyinen.viestiketju().koko(); i++){
            // Etsitään yhtäläisyyksiä Stringin contains()-metodin avulla
            if(nykyinen.viestiketju().alkio(i).toString().contains(syote)){
                // Tulostetaan viesti, mikäli yhtäläisyys löytyi
                System.out.println(nykyinen.viestiketju().alkio(i));
            }
        }
    }
}
