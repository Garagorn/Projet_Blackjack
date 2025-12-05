package blackjack.vue.composantstable;

import java.awt.*;
import javax.swing.*;
import modele.cartes.Paquet;

/**
 * Panneau affichant l'état de la pioche dans le jeu de Blackjack.
 * Ce panneau montre le paquet de cartes restant à jouer dans la pioche,
 * ainsi qu'un indicateur du nombre de cartes restantes.
 */
public class PiochePanel extends JPanel {
    /**
     *Vue représentant la pioche de cartes
     */
    private VuePaquetCache piocheVue;
    /**
     * Le paquet de cartes de la pioche
     */
    private Paquet pioche; 
    
    /**
     * Constructeur du panneau de la pioche.
     * Initialisation du panneau avec la pioche fournie.
     * 
     * @param pioche Le paquet de cartes représentant la pioche du jeu.
     */
    public PiochePanel(Paquet pioche){
        super();
        this.pioche = pioche;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0, 100, 0)); // Vert foncé pour la couleur du fond du panneau
        this.setBorder(BorderFactory.createTitledBorder("Pioche"));
        
        afficherPioche(); // Afficher la pioche au démarrage
    }
    
    /**
     * Affiche l'état actuel de la pioche.
     * Met à jour la vue de la pioche et le nombre de cartes restantes.
     */
    public void afficherPioche(){
        // Supprimer la vue précédente de la pioche, si elle existe
        if (piocheVue != null) {
            this.remove(piocheVue);
        }
        
        // Créer une nouvelle vue pour la pioche
        piocheVue = new VuePaquetCache(pioche);
        this.add(piocheVue, BorderLayout.CENTER); // Ajouter la vue de la pioche au centre du panneau
        
        // Afficher le nombre de cartes restantes dans la pioche
        JLabel lblInfo = new JLabel("Cartes restantes : " + pioche.getPaquet().size(), SwingConstants.CENTER);
        lblInfo.setForeground(Color.WHITE);
        this.add(lblInfo, BorderLayout.SOUTH);
        
        this.revalidate(); // Revalider la mise en page après la mise à jour
        this.repaint(); // Repeindre le panneau pour refléter les changements
    }
    
    /**
     * Réinitialise l'affichage de la pioche.
     * Cette méthode permet de réafficher la pioche, par exemple après un mélange ou une modification.
     */
    public void reinitialiser() {
        afficherPioche(); // Réafficher la pioche avec les informations mises à jour
    }
}
