package blackjack.vue;

import blackjack.controleur.ControleurJeu;
import blackjack.modele.jeu.Blackjack;
import javax.swing.*;
import java.awt.*;

public class JeuFrame extends JFrame {
    
    private Blackjack bj;
    private VueBlackjack vueBlackjack;     
    private VueDatas vueDatas;         
    private VueBoutons vueBoutons;       

    public JeuFrame(Blackjack bj) {
        this.bj = bj;
        
        vueBlackjack = new VueBlackjack(bj);
        vueDatas = new VueDatas();      
        vueBoutons = new VueBoutons();     
        
         
        setTitle("Jeu de Blackjack");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);   
        
        setLayout(new BorderLayout());
        
        add(vueBlackjack, BorderLayout.CENTER);
        
        JPanel panneauDonneesActions = new JPanel();
        panneauDonneesActions.setLayout(new BorderLayout());
        panneauDonneesActions.add(vueDatas, BorderLayout.NORTH);
        panneauDonneesActions.add(vueBoutons, BorderLayout.SOUTH); 
        
        add(panneauDonneesActions, BorderLayout.EAST);   
        
        setVisible(true);
    }
    public void setControleur(ControleurJeu controleur) {
         vueBoutons.setControleur(controleur);
    }

     
}
