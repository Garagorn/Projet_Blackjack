package blackjack.modele.jeu;

import java.util.Scanner;
import blackjack.modele.event.AbstractModeleEcoutable;
import modele.cartes.Paquet;
import blackjack.modele.joueurs.*;
import blackjack.modele.util.RegleDeJeu;
import modele.cartes.Carte;

public class Blackjack extends AbstractModeleEcoutable {
    
    private JoueurBlackjackH joueur;   
    private Croupier croupier;        
    private Paquet pioche;            
    private Scanner scanner;
    public Blackjack(JoueurBlackjackH joueur) {
        this.joueur = joueur;
        this.croupier = new Croupier();
        this.pioche = Paquet.creerJeu52();
        this.scanner = new Scanner(System.in);
    }
    
    public JoueurBlackjackH getJoueur(){
        return this.joueur;
    }
    public Croupier getCroupier(){
        return this.croupier;
    }
    
    public Paquet getPioche(){
        return this.pioche;
    }

     
    public void demarrer() {
        this.pioche.melanger();
        fireChangement();
        distribuer();
    }
    
    /**
     * Distribue les cartes initiales aux joueurs et au croupier.
     */
    public void distribuer() {

            Carte c1 = this.pioche.getPaquet().get(0);
            Carte c2 = this.pioche.getPaquet().get(1);
            joueur.recevoirCarte(c1);
            croupier.recevoirCarte(c2);
            this.pioche.retirer(c1);
            this.pioche.retirer(c2);
            Carte c3 = this.pioche.getPaquet().get(0);
            Carte c4 = this.pioche.getPaquet().get(1);
            joueur.recevoirCarte(c3);
            croupier.recevoirCarte(c4);
            this.pioche.retirer(c3);
            this.pioche.retirer(c4);
            
            int scoreJoueur = RegleDeJeu.calculerScore(joueur.getMain());
            joueur.setScore(scoreJoueur);
            
            int scoreCroupier = RegleDeJeu.calculerScore(croupier.getMain());
            croupier.setScore(scoreCroupier);
        
        fireChangement();
    }
    

     
    public void gererTourJoueur(String action) {
        if (RegleDeJeu.aBlackjack(joueur.getMain())) {
            return;  
        }

        if(!joueur.aDepasse21() && !joueur.getFini()) {
            switch (action) {
                case "Tirer":
                    joueur.tirerCarte(pioche);
                    break;

                case "Rester":
                    joueur.rester();
                    break;

                case "Doubler":
                    joueur.doublerMise(pioche);
                    break;

                case "Diviser":
                    joueur.diviser(pioche);
                    break;

                case "Assurance":
                    double miseAssurance = scanner.nextDouble();
                    scanner.nextLine();  // Consommer le caractère de nouvelle ligne
                    joueur.prendreAssurance(miseAssurance);
                    break;
                
                default:
                    System.out.println("action impossible");   
            }

        }

    fireChangement();
}

     
    public void evaluerScores() {
        if (joueur.aDepasse21()){
             
        }     
        croupier.tirerCarte(pioche);  
        fireChangement();
        if (croupier.aDepasse21()) {
            System.out.println("Le croupier est buste, le joueur "+joueur.getNom() +" gagne !");
        } else{
            if (joueur.getScore() > croupier.getScore() && !joueur.aDepasse21()) {
                System.out.println(joueur.getNom() + " gagne !");
            } else if (croupier.getScore() == joueur.getScore()) {
                System.out.println(joueur.getNom() + " et le croupier sont à égalité !");
            } else {
                System.out.println(joueur.getNom() + " perd !");
            }
        }
    }
}
