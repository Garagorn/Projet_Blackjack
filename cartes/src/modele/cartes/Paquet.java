/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele.cartes;
import java.util.*;
import modele.event.*;
/**
 *
 * @author siaghi231
 */
public class Paquet extends AbstractModeleEcoutable {
    private final ArrayList<Carte> paquet;
    private final static String[] TABLE_VAL_52={"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Dame", "Roi"};
    private final static String[] TABLE_VAL_32={"As", "7", "8", "9", "10", "Valet", "Dame", "Roi"};
    
    public Paquet() {
        this.paquet= new ArrayList<>();
    }

    public ArrayList<Carte> getPaquet() {
        return paquet;
    }

    public void ajouter(Carte c){
        paquet.add(c);
    }
    
    public void retirer(Carte c){
        paquet.remove(c);
    }
    
    public Carte carteRandom(){
        Random rand = new Random();
        int n = rand.nextInt(paquet.size());
        return paquet.get(n);
    }
    
    
    public Paquet melanger(){
        return Collections.shuffle(this);
    }
    
    //Couper apres les 3 premieres et avant les 3 dernieres
    public Paquet couper(){
        //Math.random() * ( paquet.size()-3 - 3 )
    }
    
    @Override
    public String toString() {
        return "Paquet{" + "paquet=" + paquet + '}';
    }
    
    //Factory methods 52 et  32
    
    public static Paquet creerJeu52() {
        return creationPaquet(TABLE_VAL_52);
    }
    
    public static Paquet creerJeu32() {
        return creationPaquet(TABLE_VAL_32);
    }        
    
    public static Paquet creationPaquet(String[] hauteurs){
        String[] couleurs = {"Trefle", "Carreau", "Coeur", "Pique"};
        Paquet p = new Paquet();
        for (String couleur : couleurs) {
            for (String valeur : hauteurs) {
                p.ajouter(new Carte(valeur, couleur));
            }
        }
    return p;
    }
}
