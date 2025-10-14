/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele.cartes;
import java.util.*;
import modele.event.*;

public class Paquet extends AbstractModeleEcoutable {
    private final ArrayList<Carte> paquet;
    private final static String[] TABLE_VAL_52={"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Dame", "Roi"};
    private final static String[] TABLE_VAL_32={"As", "7", "8", "9", "10", "Valet", "Dame", "Roi"};
    
    public Paquet() {
        this.paquet= new ArrayList<>();
    }

    /**
     * Getter de l'instance du paquet
     * @return ArrayList(Carte) Liste de cartes
     */
    public ArrayList<Carte> getPaquet() {
        return paquet;
    }

    /**
     * Ajoute une carte c au paquet sur lequel la methode est appelee
     * @param c Carte a ajouter
     */
    public void ajouter(Carte c){
        paquet.add(c);
    }
    
    /**
     * Retire une carte c au paquet sur lequel la methode est appelee
     * @param c Carte a retirer
     */
    public void retirer(Carte c){
        paquet.remove(c);
    }
    
    /**
     * Methode pour tirer aleatoirement une carte du paquet
     * @return Carte Carte du paquet
     */
    public Carte carteRandom(){
        Random rand = new Random();
        int n = rand.nextInt(paquet.size());
        return paquet.get(n);
    }
    
    /**
     * Methode qui melange aleatoirement le paquet
     */
    public void melanger(){
        Random rand = new Random();
        Collections.shuffle(paquet,rand); //Shuffle methode applicable aux listes qui sont des  collections
        //fireChangement();
    }
    
    /**
     * Couper un paquet de carte
     * Requires couper apres les 3 premieres cartes
     * Requires couper avant les  3 dernieres cartes
     */
    public void couper(){
        int taille  = paquet.size();
        ArrayList<Carte> nouveau=new ArrayList<>();
        
        int index = 3 + (int)(Math.random() * (taille - 6));
        for (int i = index; i < taille; i++) {
            nouveau.add(paquet.get(i));
        }
        
        for (int i = 0; i < index; i++) {
            nouveau.add(paquet.get(i));
        }
        paquet.clear(); //Vider le paquet avant de le reconstruire
        for (int i = 0; i < nouveau.size(); i++) {
            paquet.add(nouveau.get(i)); //Reconstruire le paquet
        }
        nouveau.clear(); //Vider le paquet de  pre-construction
        //fireChangement();
    }
        
    @Override
    public String toString() {
        return "Paquet{" + "paquet=" + paquet + '}';
    }
    
    /**
     * Factory methode pour la  creation d'un jeu de  52 cartes
     * @return Paquet Un paquet qui contient 52 cartes 
     */
    public static Paquet creerJeu52() {
        return creationPaquet(TABLE_VAL_52);
    }
    
    /**
     * Factory methode pour la  creation d'un jeu de  32 cartes
     * @return Paquet Un paquet qui contient 32 cartes 
     */
    public static Paquet creerJeu32() {
        return creationPaquet(TABLE_VAL_32);
    }        
    
    /***
     * Methode de  build pour les 2 factory methods
     * @param hauteurs Tableau pour les hauteurs des cartes
     * @return Paquet Un jeu de carte (liste de carte) de 52 ou 32 cartes 
     */
    public static Paquet creationPaquet(String[] hauteurs){
        String[] couleurs = {"Trefle", "Carreau", "Coeur", "Pique"};
        Paquet p = new Paquet();
        for (String couleur : couleurs) {
            for (String hauteur : hauteurs) {
                p.ajouter(new Carte(hauteur, couleur));
            }
        }
        return p;
    }
}
