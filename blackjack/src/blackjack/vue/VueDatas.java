package blackjack.vue;

import blackjack.modele.joueurs.JoueurBlackjackH;
import javax.swing.*;
import java.awt.*;

public class VueDatas extends JPanel {

    //private JoueurBlackjackH joueur;
    private JLabel labelNomJoueur;
    private JTextField champNomJoueur;
    private JLabel labelArgent;
    private JTextField champArgent;
    private JLabel labelMise;
    private JTextField champMise;

    
    public VueDatas(/*JoueurBlackjackH joueur*/) {
         
        //this.joueur = joueur;
        labelNomJoueur = new JLabel("Nom:");
        champNomJoueur = new JTextField(10);
        labelArgent = new JLabel("Argent:");
        champArgent = new JTextField(10);
        labelMise = new JLabel("Mise:");
        champMise = new JTextField(10);

        champNomJoueur.setEditable(false);
        champArgent.setEditable(false);
        champMise.setEditable(true);

         
        setLayout(new GridLayout(6, 1));   
        add(labelNomJoueur);
        add(champNomJoueur);
        add(labelArgent);
        add(champArgent);
        add(labelMise);
        add(champMise);
    }

    public void setNom(String nom) {
        champNomJoueur.setText(nom);
    }

    public void setArgent(double argent) {
        champArgent.setText(String.format("%.2f", argent));   
    }

    public void setMise(double mise) {
        champMise.setText(String.format("%.2f", mise));
    }

    public double getMise() {
        try {
            return Double.parseDouble(champMise.getText());
        } catch (NumberFormatException e) {
            return 0.0;  // Retourner 0 si la mise est invalide
        }
    }
}
