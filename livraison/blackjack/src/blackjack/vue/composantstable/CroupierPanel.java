package blackjack.vue.composantstable;

import blackjack.modele.joueurs.Croupier;
import java.awt.*;
import javax.swing.*;
import modele.cartes.Paquet;

/**
 * Panneau affichant les informations relatives au croupier dans le jeu de Blackjack.
 * Ce panneau affiche la main du croupier, ainsi que des informations supplémentaires telles que son score, 
 * et indique si le croupier a un Blackjack ou s'il est buste.
 */
public class CroupierPanel extends JPanel {
    
    /**Étiquette affichant le nom du croupier et son score
     * 
     */
    private JLabel lblCroupier;
    
    /**Vue de la main du croupier
     * 
     */
    private VueMainCroupier mainCroupierVue;
    
    /**Instance du croupier
     * 
     */
    private Croupier croupier;
    
    /**
     * Indicateur si les cartes du croupier doivent être révélées
     */
    private boolean cartesRevelees;
    
    /**
     * Constructeur du panneau du croupier.
     * @param croupier Le croupier à afficher dans ce panneau.
     */
    public CroupierPanel(Croupier croupier){
        super();
        this.croupier = croupier;
        
        // Configuration du panneau
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0, 100, 0));  // Fond vert
        this.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Bordure rouge
        
        // Configuration de l'étiquette du croupier
        lblCroupier = new JLabel("Croupier", SwingConstants.CENTER);
        lblCroupier.setFont(new Font("Arial", Font.BOLD, 16));
        lblCroupier.setForeground(Color.WHITE);
        this.add(lblCroupier, BorderLayout.NORTH);
    }
    
    /**
     * Affiche la main du croupier.
     * Si les cartes du croupier sont révélées, elles seront toutes visibles, sinon seule la première carte sera affichée.
     */
    public void afficherMainCroupier(){
        // Vérifier si les cartes doivent être révélées
        this.cartesRevelees = croupier.getRevelerDeuxiemeCarte();
        
        // Retirer l'ancienne vue de la main du croupier si elle existe
        if (mainCroupierVue != null) {
            this.remove(mainCroupierVue);
        }
        
        // Si le croupier n'a pas de main, afficher une main vide
        if (croupier == null || croupier.getMain() == null) {
            mainCroupierVue = new VueMainCroupier(new Paquet(), cartesRevelees);
        } else {
            // Récupérer le paquet de cartes du croupier
            Paquet mainCroupier = croupier.getMain().getPaquetMain();
            mainCroupierVue = new VueMainCroupier(mainCroupier, cartesRevelees);
            
            // Mise à jour du texte de l'étiquette du croupier
            String info = "Croupier";
            if (cartesRevelees) {
                info += " - Score: " + croupier.getScore();
                if (croupier.estBuste()) {
                    info += " (BUSTE!)";
                } else if (croupier.aBlackjack()) {
                    info += " (BLACKJACK!)";
                }
            } else {
                info += " - Score visible: " + croupier.getScoreVisible();
            }
            lblCroupier.setText(info);
        }
        
        // Ajouter la vue de la main du croupier au panneau
        this.add(mainCroupierVue, BorderLayout.CENTER);
        
        // Revalidate et repaint pour actualiser l'affichage
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Réinitialise l'affichage du panneau en affichant à nouveau la main du croupier.
     */
    public void reinitialiser() {
        afficherMainCroupier();
    }
}
