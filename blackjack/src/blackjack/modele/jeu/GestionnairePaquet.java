package blackjack.modele.jeu;

import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import modele.cartes.Carte;
import modele.cartes.Paquet;

/**
 * Gestionnaire dédié au paquet de cartes
 */
public class GestionnairePaquet {
    private Paquet paquet;
    
    
    public GestionnairePaquet() {
        this.paquet = Paquet.creerJeu52();
        this.paquet.melanger();
    }
     
    
    public void distribuerCarteJoueurs(Joueur joueur) {
        if (paquet.estVide()) {
            remelanger();
        }
        
        Carte carte = paquet.getPaquet().get(0);
        joueur.ajouterCarte(carte);
        paquet.retirer(carte);
    }
    
    public void distribuerCarteCroupier(Croupier croupier) {
        if (paquet.estVide()) {
            remelanger();
        }
        
        Carte carte = paquet.getPaquet().get(0);
        croupier.ajouterCarte(carte);
        paquet.retirer(carte);
    }
    
    /**
     * Vérifie si le paquet doit être remélangé
     */
    public boolean doitRemelanger() {
        return paquet.estVide() ;
    }
    
    /**
     * Remélange le paquet
     */
    public void remelanger() {
        this.paquet = Paquet.creerJeu52();
        this.paquet.melanger();
    }
    
    /**
     * Getter du paquet
     */
    public Paquet getPaquet() {
        return paquet;
    }
}