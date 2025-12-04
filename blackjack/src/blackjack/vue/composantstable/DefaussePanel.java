package blackjack.vue.composantstable;
import java.awt.*;
import javax.swing.*;
import modele.cartes.Paquet;

/**
 * Panneau d'affichage pour la défausse (tas de cartes jouées).
 * Affiche le paquet de défausse avec un compteur de cartes.
 */
public class DefaussePanel extends JPanel {
    
    /**
     * la Vue de la defausse (swings)
     */
    private VuePaquetCache defausseVue;
    /**
     *référence sur la defausse
     */
    private Paquet defausse;
    
    /**
     * Constructeur du panneau de défausse.
     * 
     * @param defausse le paquet de cartes à afficher comme défausse
     */
    public DefaussePanel(Paquet defausse){
        super();
        this.defausse = defausse;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0, 100, 0));
        this.setBorder(BorderFactory.createTitledBorder("Défausse"));
        afficherDefausse();
    }
    
    /**
     * Affiche ou met à jour l'affichage de la défausse.
     * Crée une vue graphique du paquet de défausse et ajoute un compteur de cartes.
     */
    public void afficherDefausse(){
        if (defausseVue != null) {
            this.remove(defausseVue);
        }
        
        defausseVue = new VuePaquetCache(defausse);
        this.add(defausseVue, BorderLayout.CENTER);
        
        JLabel lblInfo = new JLabel("Cartes: " + defausse.getPaquet().size(), SwingConstants.CENTER);
        System.out.println(defausse.getPaquet().size());
        lblInfo.setForeground(Color.WHITE);
        this.add(lblInfo, BorderLayout.SOUTH); 
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Réinitialise l'affichage de la défausse.
     * Méthode appelée lors d'une nouvelle partie.
     */
    public void reinitialiser() {
        afficherDefausse();
    }
}