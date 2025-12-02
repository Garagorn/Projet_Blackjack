package modele.cartes;

import java.util.*;
import modele.event.*;

/**
 * Représente un paquet de cartes, avec des méthodes pour manipuler ce paquet.
 * Ce paquet peut contenir un jeu de 32 ou 52 cartes.
 * Hérite d'AbstractModeleEcoutable pour notifier les vues en cas de changement.
 * 
 * @author tellier212
 */
public class Paquet extends AbstractModeleEcoutable {
    
    /**
     * Liste des cartes dans le paquet.
     */
    private final ArrayList<Carte> paquet;
    
    /**
     * Valeurs possibles pour un jeu de 52 cartes.
     */
    private final static String[] TABLE_VAL_52 = {
        "As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Dame", "Roi"
    };
    
    /**
     * Valeurs possibles pour un jeu de 32 cartes.
     */
    private final static String[] TABLE_VAL_32 = {
        "As", "7", "8", "9", "10", "Valet", "Dame", "Roi"
    };
    
    /**
     * Constructeur par défaut, crée un paquet vide.
     */
    public Paquet() {
        this.paquet = new ArrayList<>();
    }

    /**
     * Renvoie la liste des cartes contenues dans le paquet.
     * 
     * @return Liste des cartes dans le paquet.
     */
    public ArrayList<Carte> getPaquet() {
        return this.paquet;
    }

    /**
     * Ajoute une carte au paquet.
     * Notifie les observateurs du modèle.
     * 
     * @param c Carte à ajouter.
     */
    public void ajouter(Carte c){
        this.paquet.add(c);
        fireChangement();
    }
    
    /**
     * Retire une carte du paquet.
     * Notifie les observateurs du modèle.
     * 
     * @param c Carte à retirer.
     */
    public void retirer(Carte c){
        this.paquet.remove(c);
        fireChangement();
    }
    
    /**
     * Tire une carte aléatoire dans le paquet sans la retirer.
     * 
     * @return Une carte aléatoire du paquet.
     */
    public Carte carteRandom(){
        Random rand = new Random();
        int n = rand.nextInt(this.paquet.size());
        return this.paquet.get(n);
    }
   
    
    /**
     * Mélange aléatoirement les cartes du paquet.
     * Notifie les observateurs du modèle.
     */
    public void melanger(){
        Random rand = new Random();
        Collections.shuffle(this.paquet, rand);
        fireChangement();
    }
    
    /**
     * Renvoie le nombre de cartes actuellement dans le paquet.
     * 
     * @return Nombre de cartes dans le paquet.
     */
    public int nbr_carte(){
        return this.paquet.size();
    }
    
    /**
     * Coupe le paquet à un index aléatoire compris entre le 4e et l'avant-dernier carte,
     * puis reconstitue le paquet avec cette coupure.
     * Notifie les observateurs du modèle.
     */
    public void couper(){
        int taille  = this.paquet.size();
        ArrayList<Carte> nouveau = new ArrayList<>();
        
        int index = 3 + (int)(Math.random() * (taille - 6)); // Index entre 3 et taille-3
        for (int i = index; i < taille; i++) {
            nouveau.add(paquet.get(i));
        }
        
        for (int i = 0; i < index; i++) {
            nouveau.add(paquet.get(i));
        }
        
        paquet.clear();
        paquet.addAll(nouveau);
        nouveau.clear();
        
        fireChangement();
    }
    
    
        
    @Override
    /**
     * Méthode pour l'affichage d'un paquet
     * Elle affiche toutes les cartes du paquet
     */
    public String toString() {
        return "Paquet : {" + paquet + '}';
    }
    
    
    /**
     * Méthode pour savoir si le paquet est vide     
     * @return booleen : True si le paquet est vide, False sinon
     */
    public boolean estVide(){
        return this.paquet.isEmpty();
    }
    
    /**
     * Methode de test sur paquet, pour savoir si il contient une carte
     * @param c La carte  que l'on cherche dans le paquet
     * @return boolean : True si la carte se trouve  dans le paquet ou  False sinon
     */
    public boolean contient(Carte c){
        return paquet.contains(c);
    }
    
    /**
     * Crée un paquet complet de 52 cartes.
     * 
     * @return Paquet contenant 52 cartes.
     */
    public static Paquet creerJeu52() {
        return creationPaquet(TABLE_VAL_52);
    }
    
    /**
     * Crée un paquet complet de 32 cartes.
     * 
     * @return Paquet contenant 32 cartes.
     */
    public static Paquet creerJeu32() {
        return creationPaquet(TABLE_VAL_32);
    }        
    
    /**
     * Méthode utilitaire pour créer un paquet avec un tableau de hauteurs donné.
     * 
     * @param hauteurs Tableau des hauteurs des cartes à créer.
     * @return Paquet complet avec les hauteurs et toutes les couleurs.
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
