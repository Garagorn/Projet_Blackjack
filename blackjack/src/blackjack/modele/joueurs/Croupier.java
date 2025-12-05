package blackjack.modele.joueurs;

import modele.cartes.Carte;
import modele.cartes.Paquet;

/**
 * Classe concrète représentant le croupier dans le jeu de Blackjack.
 * @author siaghi231@CAMPUS
 *
 */
public class Croupier {
    
    private static final int SCORE_MINIMUM = 17;
    private Main main;
    private boolean revelerDeuxiemeCarte;
    
    /**
     * Constructeur du croupier.
     * Initialisation de la main et de l'état de la deuxième carte (cachée).
     */
    public Croupier() {
        this.revelerDeuxiemeCarte = false;
        this.main = new Main();
    }
    
    /**
     * Ajoute une carte à la main du croupier.
     * 
     * @param carte La carte à ajouter à la main du croupier
     */
    public void ajouterCarte(Carte carte) {
        main.ajouterCarte(carte);
    }
    
    /**
     * Exécute le tour du croupier en tirant des cartes selon les règles classiques :
     * Le croupier tire des cartes jusqu'à atteindre un score d'au moins 17.
     * 
     * @param pioche Le paquet de cartes à partir duquel le croupier peut tirer.
     */
    public void jouer(Paquet pioche) {
        while (getScore() < SCORE_MINIMUM) {
            if (pioche.estVide()) break;
            
            Carte carte = pioche.getPaquet().get(0);
            ajouterCarte(carte);
            pioche.retirer(carte);
        }
    }
    
    /**
     * Révèle la deuxième carte du croupier.
     * Cette méthode est appelée pour afficher la carte cachée du croupier.
     */
    public void afficherDeuxiemeCarte() {
        this.revelerDeuxiemeCarte = true;
    }
    
    /**
     * Cache la deuxième carte du croupier.
     * Cette méthode est appelée pour cacher la carte visible du croupier avant qu'elle soit révélée.
     */
    public void cacherDeuxiemeCarte() {
        this.revelerDeuxiemeCarte = false;
    }
    
    /**
     * Indique si la deuxième carte du croupier est visible ou non.
     * 
     * @return `true` si la deuxième carte est visible, sinon `false`.
     */
    public boolean getRevelerDeuxiemeCarte() {
        return this.revelerDeuxiemeCarte;
    }
    
    /**
     * Obtient la première carte de la main du croupier (carte visible).
     * 
     * @return La première carte de la main du croupier, ou `null` si la main est vide.
     */
    public Carte getCarteVisible() {
        return main.getPaquetMain().getPaquet().isEmpty() ? null : main.getPaquetMain().getPaquet().get(0);
    }
    
    /**
     * Retourne le score visible du croupier (uniquement basé sur la première carte).
     * Ce score est utilisé pour afficher la carte visible du croupier avant de révéler sa main complète.
     * 
     * @return Le score de la première carte visible du croupier.
     */
    public int getScoreVisible() {
        Carte carteVisible = getCarteVisible();
        return carteVisible != null ? calculerValeurCarte(carteVisible) : 0;
    }
    
    /**
     * Calcule la valeur d'une carte selon ses règles dans le Blackjack :
     * - As vaut 11
     * - Valet, Dame, Roi valent 10
     * - Les autres cartes valent leur valeur numérique.
     * 
     * @param carte La carte dont on veut calculer la valeur.
     * @return La valeur de la carte (10, 11 ou la valeur numérique).
     */
    private int calculerValeurCarte(Carte carte) {
        switch (carte.hauteur) {
            case "As":
                return 11;
            case "Valet":
            case "Dame":
            case "Roi":
                return 10;
            default:
                try {
                    return Integer.parseInt(carte.hauteur);
                } catch (NumberFormatException e) {
                    return 0;
                }
        }
    }
    
    /**
     * Vérifie si le croupier a un Blackjack.
     * Un Blackjack consiste en une combinaison d'un As et d'une carte de valeur 10.
     * 
     * @return `true` si le croupier a un Blackjack, sinon `false`.
     */
    public boolean aBlackjack() {
        return main.estBlackjack();
    }
    
    /**
     * Vérifie si le croupier a dépassé 21 points et est donc "buste".
     * 
     * @return `true` si le croupier est buste, sinon `false`.
     */
    public boolean estBuste() {
        return main.estBuste();
    }
    
    /**
     * Réinitialise la main du croupier, en la vidant de toutes les cartes et réinitialisant les scores.
     */
    public void reinitialiserMain() {
        main.reinitialiser();
    }
    
    /**
     * Obtient le score total du croupier.
     * 
     * @return Le score actuel de la main du croupier.
     */
    public int getScore() {
        return main.getScore();
    }
    
    /**
     * Retourne l'objet représentant la main complète du croupier.
     * 
     * @return L'objet `Main` qui représente les cartes du croupier.
     */
    public Main getMain() {
        return main;
    }
    
    /**
     * Retourne un paquet contenant uniquement la première carte de la main du croupier,
     * qui est visible (utilisé pour afficher la carte visible du croupier).
     * 
     * @return Un paquet contenant uniquement la carte visible du croupier.
     */
    public Paquet getPaquetVisible() {
        Paquet paquetVisible = new Paquet();
        if (!main.getPaquetMain().getPaquet().isEmpty()) {
            paquetVisible.ajouter(main.getPaquetMain().getPaquet().get(0));
        }
        return paquetVisible;
    }
    
    /**
     * Retourne une chaîne de caractères représentant l'état actuel du croupier.
     * Cela inclut son score, s'il a un Blackjack ou est buste.
     * 
     * @return La représentation sous forme de chaîne de caractères du croupier.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Croupier [");
        sb.append("Score: ").append(main.getScore());
        if (aBlackjack()) sb.append(", BLACKJACK!");
        if (estBuste()) sb.append(", BRÛLÉ!");
        sb.append("]");
        return sb.toString();
    }
}
