package blackjack.vue.composantscontrol;

import blackjack.controleur.ControleurBlackjack;
import java.awt.*;
import javax.swing.*;

/**
 * Panneau permettant de placer la mise dans le jeu de Blackjack.
 * Ce panneau contient un champ de texte pour entrer la mise et un bouton pour la valider.
 * La mise est envoyée au contrôleur lorsque l'utilisateur clique sur le bouton "Placer mise".
 */
public class MisePanel extends JPanel {
    /**
    * une étiquette pour la mise 
    */
    private JLabel lblMise;
    /**
     * le champ du texte pour saisir la mise
     */
    private JTextField champMise;
    /**
     * le bouton qui soumet la mise
     */
    private JButton btnMiser;
    /**
    * reférence sur le controleur pour gérer la mise  
    */
    private ControleurBlackjack controleur;

    /**
     * Constructeur de MisePanel.
     * Ce constructeur initialise les composants graphiques du panneau de mise,
     * y compris un champ de texte pour la mise, un bouton pour valider la mise,
     * et un label pour afficher "Mise : ".
     * Lorsque l'utilisateur clique sur "Placer mise", la mise est envoyée au contrôleur
     * pour effectuer l'action associée.
     */
    public MisePanel() {
        super(new FlowLayout(FlowLayout.LEFT));
        this.controleur = null; // Le contrôleur est initialisé à null
        this.lblMise = new JLabel("Mise : ");
        this.champMise = new JTextField("100", 8);  // Mise par défaut de 100
        this.btnMiser = new JButton("Placer mise");
        
        // Action à effectuer lorsqu'on clique sur le bouton "Placer mise"
        this.btnMiser.addActionListener(e -> {
            if (controleur != null) {
                try {
                    // Récupérer la mise entrée par l'utilisateur et la convertir en entier
                    int mise = Integer.parseInt(champMise.getText());
                    this.controleur.actionMiser(mise); // Appeler l'action sur le contrôleur
                } catch (NumberFormatException ex) {
                    // Afficher un message d'erreur si la mise n'est pas un nombre valide
                    JOptionPane.showMessageDialog(this, 
                        "Veuillez entrer un nombre valide", 
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ajouter les composants au panneau
        this.add(lblMise);
        this.add(champMise);
        this.add(btnMiser);
    }

    /**
     * Associe un contrôleur à ce panneau de mise.
     * Le contrôleur est utilisé pour gérer les actions liées à la mise.
     *
     * @param controleur Le contrôleur qui gère les actions de mise.
     */
    public void setControleur(ControleurBlackjack controleur) {
        this.controleur = controleur;
    }

    /**
     * Récupère le champ de texte pour la mise.
     * Permet d'accéder au champ où l'utilisateur peut entrer la mise.
     *
     * @return Le champ de texte pour la mise.
     */
    public JTextField getChampMise() {
        return champMise;
    }

    /**
     * Récupère le bouton permettant de placer la mise.
     * Ce bouton permet à l'utilisateur de valider la mise qu'il a entrée.
     *
     * @return Le bouton pour placer la mise.
     */
    public JButton getBtnMiser() {
        return btnMiser;
    }
}
