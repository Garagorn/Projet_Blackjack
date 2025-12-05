package modele.cartes;

/**
 * Implémentation de l'interface {@link Joueur} représentant un joueur humain.
 * 
 * Un joueur humain possède un nom, un paquet de cartes en main et un score.
 * Cette classe permet de gérer la réception de cartes et l'accès aux informations du joueur.
 * 
 * @author tellier212
 */
public class JoueurHumain implements Joueur {

    private final String nom;
    private final Paquet main;
    
    /**
     * Constructeur pour créer un joueur humain avec un nom donné.
     * La main est initialisée vide et le score à zéro.
     * 
     * @param nom Le nom du joueur humain.
     */
    public JoueurHumain(String nom) {
        this.nom = nom;
        this.main = new Paquet();
    }

    /**
     * Renvoie le nom du joueur.
     * 
     * @return Le nom du joueur sous forme de chaîne.
     */
    @Override
    public String getNom() {
        return nom;
    }

    /**
     * Récupère la main du joueur.
     * 
     * @return Le paquet représentant la main du joueur.
     */
    @Override
    public Paquet getMain() {
        return main;
    }

    
    //public Carte jouerCarte() { } // À implémenter si nécessaire
    
    /**
     * Ajoute une carte reçue à la main du joueur.
     * 
     * @param c La carte à ajouter.
     */
    @Override
    public void recevoirCarte(Carte c) {
        main.ajouter(c);
    }

    /**
     * Représentation textuelle du joueur humain.
     * 
     * @return Une chaîne décrivant le joueur, sa main et son score.
     */
    @Override
    public String toString() {
        return "Je suis un joueur humain : " + nom + ", j'ai en main= " + main + " .";
    }
}