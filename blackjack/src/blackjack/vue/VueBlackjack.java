package blackjack.vue;

import blackjack.modele.jeu.Blackjack;
import blackjack.modele.joueurs.*;
import blackjack.modele.event.EcouteurModele;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueBlackjack  extends JPanel implements EcouteurModele{
    
    private Blackjack bj;
    private JLabel labelScoreJoueur;
    private JLabel labelScoreCroupier;
    private VuePioche vuePioche;
    private VueMainCroupier vmc;
    private VueMainJoueur vmj;
    
    
    public VueBlackjack(Blackjack bj){
        this.bj = bj;
        this.bj.ajouterEcouteur(this);
        vuePioche = new VuePioche(bj.getPioche(), "PIOCHE");
        vmc = new VueMainCroupier(bj.getCroupier().getMain(), false, "Main Croupier");
        vmj = new VueMainJoueur(bj.getJoueur().getMain(), false, "Main Joueur");

        labelScoreJoueur = new JLabel("Score Joueur: " + bj.getJoueur().getScore());
        labelScoreCroupier = new JLabel("Score Croupier: " + bj.getCroupier().getScore());

         
        JPanel panelCentre = new JPanel();
        panelCentre.setLayout(new GridLayout(1, 3));  
        panelCentre.add(vuePioche);
        panelCentre.add(vmc);
        panelCentre.add(vmj);
        
        add(panelCentre, BorderLayout.CENTER);
        
        JPanel panelScores = new JPanel();
        panelScores.setLayout(new GridLayout(2, 1));  
        panelScores.add(labelScoreJoueur);
        panelScores.add(labelScoreCroupier);
        
        add(panelScores, BorderLayout.SOUTH);
    }
    
    
    @Override
    public void modeleMiseAJour(Object source) {
        JoueurBlackjackH joueur = bj.getJoueur();
        Croupier croupier = bj.getCroupier();
        labelScoreJoueur.setText("Score Joueur : " + joueur.getScore());
        labelScoreCroupier.setText("Score Croupier: " + croupier.getScore());
         
    }

}