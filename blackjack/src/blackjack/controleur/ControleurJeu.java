package blackjack.controleur;

import blackjack.modele.jeu.Blackjack;
import blackjack.modele.joueurs.JoueurBlackjackH;
import blackjack.vue.JeuFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ControleurJeu implements ActionListener{
    private Blackjack bj;
    private JeuFrame fenetre;
    

    public ControleurJeu(Blackjack bj, JeuFrame fenetre){
        this.bj = bj;
        this.fenetre = fenetre;
    }
    
    
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        JoueurBlackjackH joueur = this.bj.getJoueur();
        this.bj.gererTourJoueur(action);
        if(joueur.aDepasse21() || joueur.getFini()){
            this.bj.evaluerScores();
        }
    }
}