package blackjack.modele.joueurs;

import blackjack.modele.util.RegleDeJeu;
import modele.cartes.Carte;
import modele.cartes.Paquet;

public class Croupier implements JoueurBlackjack {

    private String nom;
    private int score;
    private Paquet main;
    private boolean aFini;

    public Croupier() {
        this.nom = "Le Croupier";
        this.main = new Paquet();
        this.score = 0;
        this.aFini = false;
    }

    @Override
    public boolean getFini() {
        return this.aFini;
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public Paquet getMain() {
        return this.main;
    }

    @Override
    public void recevoirCarte(Carte c) {
        main.ajouter(c);
    }

    public int getScore() {
        return this.score;
    }
    public void setScore(int sc){
    
        this.score = sc;
    }
    public boolean aDepasse21() {
        if (this.score > 21) {
            aFini = true;
            return true;
        }
        return false;
    }

    public void tirerCarte(Paquet pioche) {
        while(this.score < 17){
            Carte carte = pioche.getPaquet().get(0);
            this.recevoirCarte(carte);
            pioche.retirer(carte);
            int scoreCroupier = RegleDeJeu.calculerScore(this.getMain());
            this.setScore(scoreCroupier);
        }
        aFini = true;
    }

}
