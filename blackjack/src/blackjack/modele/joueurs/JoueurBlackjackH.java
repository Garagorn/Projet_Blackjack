package blackjack.modele.joueurs;

import blackjack.modele.util.RegleDeJeu;
import modele.cartes.Carte;
import modele.cartes.Paquet;

public class JoueurBlackjackH implements JoueurBlackjack {

    private String nom;
    private int score;
    private Paquet main;
    private boolean aFini;
    private double mise; 
    private double argent;   

    public JoueurBlackjackH(String nom, double argent) {
        this.nom = nom;
        this.main = new Paquet();
        this.score = 0;
        this.aFini = false;
        this.mise = 0;   
        //this.miseAssurance = 0;
        this.argent = argent; 
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
    
    public double getArgent() {
        return this.argent;
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
    
    @Override
    public void recevoirCarte(Carte c) {
        main.ajouter(c);
    }
    public void tirerCarte(Paquet pioche){
        if(!pioche.estVide()){
            Carte carte = pioche.getPaquet().get(0);
            this.recevoirCarte(carte);
            pioche.retirer(carte);
            int scoreJoueur = RegleDeJeu.calculerScore(this.main);
            setScore(scoreJoueur);
        }
    }
    
    public void rester() {
        this.aFini = true;  
    }
    
    // Définir la mise du joueur au début du jeu
    
    public void setMise(double mise) {
        if (mise <= argent) {
            this.mise = mise;
            argent -= mise;  // Réduire l'argent du joueur
        } else {
            System.out.println("Vous n'avez pas assez d'argent !");
        }
    }
    
    public void doublerMise(Paquet pioche) {
    if (this.argent >= this.mise) {
        this.mise *= 2;  
        this.argent -= this.mise;
        this.tirerCarte(pioche);
        this.rester();
    } else {
        System.out.println("Vous n'avez pas assez d'argent pour doubler.");
    }
}

    

    public void prendreAssurance(double miseAssurance) {
        if (miseAssurance <= this.argent) {
            this.argent -= miseAssurance;  // Réduire l'argent du joueur
            System.out.println("Vous avez pris une assurance de " + miseAssurance + "€.");
        } else {
            System.out.println("Vous n'avez pas assez d'argent pour prendre une assurance.");
        }
    }

     

    // Diviser une paire (si applicable)
    public void diviser(Paquet pioche) {
        if (this.main.getPaquet().size() == 2 && 
            this.main.getPaquet().get(0).hauteur.equals(this.main.getPaquet().get(1).hauteur)) {

            // Créer deux nouvelles mains pour le joueur
            Paquet nouvelleMain1 = new Paquet();
            Paquet nouvelleMain2 = new Paquet();

            // Déplacer les deux cartes dans les nouvelles mains
            Carte carte1 = this.main.getPaquet().get(0);
            Carte carte2 = this.main.getPaquet().get(1);
            nouvelleMain1.ajouter(carte1);
            nouvelleMain2.ajouter(carte2);
            this.main.retirer(carte1);
            this.main.retirer(carte2);

            // Tirer une carte pour chaque main
            Carte carte3 = pioche.getPaquet().get(0);
            nouvelleMain1.ajouter(carte3);
            pioche.retirer(carte3);

            Carte carte4 = pioche.getPaquet().get(0);
            nouvelleMain2.ajouter(carte4);
            pioche.retirer(carte4);

            // Vous pouvez maintenant jouer chaque main séparément dans une boucle ou une méthode ultérieure
            System.out.println("Vous avez divisé vos cartes en deux mains.");
            System.out.println("Main 1: " + nouvelleMain1);
            System.out.println("Main 2: " + nouvelleMain2);
        } else {
            System.out.println("Vous ne pouvez pas diviser ces cartes.");
        }
    }


    public double getMise() {
        return this.mise;
    }
   
    

     
}
