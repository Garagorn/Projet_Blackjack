
package modele.cartes;

import java.util.Random;

/**
 * Implémentation de l'interface {@link Joueur} représentant un joueur IA (bot).
 * 
 * Un joueur IA possède un nom, une main (paquet de cartes), un score, et utilise un générateur
 * de nombres aléatoires pour choisir une carte à jouer.
 * 
 * @author tellier212
 */
public class JoueurIA implements Joueur {
    
    private final String nom;
    private final Paquet main;
    private final int score;
    private final Random rand;
    
    /**
     * Constructeur pour créer un joueur IA avec un nom donné.
     * La main est initialisée vide, le score à zéro, et le générateur aléatoire instancié.
     * 
     * @param nom Le nom du joueur IA.
     */
    public JoueurIA(String nom) {
        this.nom = nom;
        this.main = new Paquet();
        this.score = 0;
        this.rand = new Random();
    }
    
    /**
     * Renvoie le nom du joueur IA.
     * 
     * @return Le nom du joueur IA sous forme de chaîne.
     */
    @Override
    public String getNom() {
        return nom;
    }
    
    /**
     * Récupère la main du joueur IA.
     * 
     * @return Le paquet représentant la main du joueur IA.
     */
    @Override
    public Paquet getMain() {
        return main;
    }
    
    /**
     * Récupère le score actuel du joueur IA.
     * 
     * @return Le score du joueur IA.
     */
    @Override
    public int getScore() {
        return this.score;
    }
    
    //public Carte jouerCarte() { } // Cette méthode est ici implémentée en-dessous
    
    /**
     * Ajoute une carte reçue à la main du joueur IA.
     * 
     * @param c La carte à ajouter.
     */
    @Override
    public void recevoirCarte(Carte c) {
        main.ajouter(c);
    }
    
    /**
     * Joue une carte aléatoirement parmi celles en main.
     * La carte est retirée de la main et retournée.
     * 
     * @return La carte jouée.
     */
 
    public Carte jouerCarte() {
        int i = rand.nextInt(main.getPaquet().size());
        return main.getPaquet().remove(i);
    }
    
    /**
     * Représentation textuelle du joueur IA.
     * 
     * @return Une chaîne décrivant le joueur IA, sa main et son score.
     */
    @Override
    public String toString() {
        return "Je suis un bot : " + nom + ", j'ai en main= " + main + ", et j'ai " + score + " de score.";
    }
}
