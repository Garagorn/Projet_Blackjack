package blackjack.vue;

import blackjack.controleur.ControleurBlackjack;
import java.awt.*;
import javax.swing.*;

public class NouvellePartiePanel extends JPanel{
    
     private final JButton btnNouvellePartie;
     private ControleurBlackjack controleur;
    public NouvellePartiePanel(){
        super(new FlowLayout(FlowLayout.CENTER));
        btnNouvellePartie = new JButton("Nouvelle Partie");
        btnNouvellePartie.setFont(new Font("Arial", Font.BOLD, 14));
        btnNouvellePartie.setBackground(new Color(34, 139, 34));
        btnNouvellePartie.addActionListener(e -> {
            if (controleur != null) {
                controleur.actionNouvellePartie();
            }
        });
        
        this.add(btnNouvellePartie);
    }
     public void setControleur(ControleurBlackjack controleur) {
        this.controleur = controleur;
    }
}