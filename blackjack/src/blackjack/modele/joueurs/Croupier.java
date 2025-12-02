package blackjack.modele.joueurs;

import modele.cartes.Carte;
import modele.cartes.Paquet;

public class Croupier {
    
    private static final int SCORE_MINIMUM = 17;
    private Main main;
    private boolean revelerDeuxiemeCarte;
    public Croupier() {
        this.revelerDeuxiemeCarte = false;
        this.main = new Main();
    }
    
    public void ajouterCarte(Carte carte) {
        main.ajouterCarte(carte);
    }
    
    /**
     * Le croupier joue selon les règles standard : il tire jusqu'à atteindre 17 ou plus.
     * @param pioche le paquet depuis lequel tirer les cartes
     */
    public void jouer(Paquet pioche) {
        while (getScore() < SCORE_MINIMUM) {
            if (pioche.estVide()) break;
            
            Carte carte = pioche.getPaquet().get(0);
            ajouterCarte(carte);
            pioche.retirer(carte);
        }
    }
    
    public void afficherDeuxiemeCarte() {
        this.revelerDeuxiemeCarte = true;
    }
    public void  cacherDeuxiemeCarte(){
        this.revelerDeuxiemeCarte = false;
    }
    
    public boolean getRevelerDeuxiemeCarte(){
        return this.revelerDeuxiemeCarte;
    }
   
    public Carte getCarteVisible() {
        return main.getPaquetMain().getPaquet().isEmpty() ? null : main.getPaquetMain().getPaquet().get(0);
    }
    
    /**
     * Retourne le score visible du croupier (uniquement la première carte).
     */
    public int getScoreVisible() {
        Carte carteVisible = getCarteVisible();
        return carteVisible != null ? calculerValeurCarte(carteVisible) : 0;
    }
    
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
    
    public boolean aBlackjack() {
        return main.estBlackjack();
    }
    
    public boolean estBuste() {
        return main.estBuste();
    }
    
    public void reinitialiserMain() {
        main.reinitialiser();
    }
    
    public int getScore() {
        return main.getScore();
    }
    
    public Main getMain() {
        return main;
    }
    
    /**
     * Retourne le paquet visible (pour l'affichage avec cartes cachées)
     * @return 
     */
    public Paquet getPaquetVisible() {
        Paquet paquetVisible = new Paquet();
        if (!main.getPaquetMain().getPaquet().isEmpty()) {
            paquetVisible.ajouter(main.getPaquetMain().getPaquet().get(0));
        }
        return paquetVisible;
    }
    
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