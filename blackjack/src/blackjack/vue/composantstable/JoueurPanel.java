package blackjack.vue.composantstable;

import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import java.awt.*;
import javax.swing.*;
import modele.cartes.Paquet;

/**
 * Panneau représentant un joueur dans le jeu de Blackjack.
 * Ce panneau affiche le nom du joueur, ses mains de cartes ainsi que le score du joueur.
 * Il met en évidence la main actuelle du joueur et affiche des informations supplémentaires
 * comme si le joueur a un Blackjack ou s'il est buste.
 */
public class JoueurPanel extends JPanel {
    
    /**
     * Étiquette affichant le nom du joueur et son score
     *
     */
    private final JLabel lblJoueur;
    
    /**
     * Conteneur des mains du joueur
     * 
     */
    private MainsPanel mainsConteneur;
    
    /**
     * Vue d'une main du joueur
     */
    private VueMainJoueur mainJoueurVue;
    
    /**
     * Instance du joueur à afficher dans ce panneau
     * 
     */
    private Joueur joueur;
    
    /**
     * Constructeur du panneau pour afficher les informations d'un joueur.
     * @param joueur Le joueur à afficher dans ce panneau.
     */
    public JoueurPanel(Joueur joueur){
        super();
        this.joueur = joueur;
        
        // Configuration du panneau
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0, 100, 0));  // Fond vert
        this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));  // Bordure jaune
        
        // Initialisation du conteneur des mains
        this.mainsConteneur = new MainsPanel();
        this.add(mainsConteneur, BorderLayout.CENTER);
        
        // Initialisation de l'étiquette pour afficher le nom et score du joueur
        lblJoueur = new JLabel("Joueur", SwingConstants.CENTER);
        lblJoueur.setFont(new Font("Arial", Font.BOLD, 16));
        lblJoueur.setForeground(Color.WHITE);
        this.add(lblJoueur, BorderLayout.NORTH);
        
        // Affichage de la main du joueur
        afficherMainJoueur();
    }

    /**
     * Affiche les mains du joueur ainsi que son score.
     * Si le joueur a plusieurs mains (dans le cas d'un split), elles seront affichées séparément.
     * La main actuellement active du joueur est mise en évidence.
     */
    public void afficherMainJoueur(){
         mainsConteneur.removeAll();
         
        // Si le joueur n'a pas de mains, afficher un message
        if (this.joueur == null || this.joueur.getMains() == null || this.joueur.getMains().isEmpty()) {
            JLabel lblVide = new JLabel("Aucune main", SwingConstants.CENTER);
            lblVide.setForeground(Color.WHITE);
            mainsConteneur.add(lblVide);
  
        } else {
            // Pour chaque main du joueur, afficher la main et les informations associées
            for (int i = 0; i < joueur.getMains().size(); i++) {
                Main main = joueur.getMains().get(i);
                VueMainJoueur vueMain = new VueMainJoueur(main.getPaquetMain());
                MainPanel mainPanneau = new MainPanel();
                
                // Ajouter un label avec le numéro de la main et si elle est active
                String infoMain = "Main " + (i + 1);
                if (i == joueur.getMainActuelleIndex()) {
                    infoMain += " (ACTUELLE)";
                    mainPanneau.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));  // Mise en évidence de la main actuelle
                }
                
                // Ajouter l'étiquette et la vue de la main dans le panneau
                JLabel lblMain = new JLabel(infoMain, SwingConstants.CENTER);
                lblMain.setForeground(Color.WHITE);
                mainPanneau.add(lblMain, BorderLayout.NORTH);
                mainPanneau.add(vueMain, BorderLayout.CENTER);
                
                mainsConteneur.add(mainPanneau);
            }
          
            // Mettre à jour l'étiquette avec le nom et score du joueur, ainsi que son état (Buste ou Blackjack)
            String info = this.joueur.getNom() + " - Score: " + joueur.getScore();
            if (joueur.getMainActuelle().estBuste()) {
                info += " (BUSTE!)";
            } else if (joueur.getMainActuelle().estBlackjack()) {
                info += " (BLACKJACK!)";
            }
            lblJoueur.setText(info);
        }
        
        // Rafraîchir l'affichage du panneau
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Réinitialise l'affichage du panneau en actualisant la main du joueur.
     */
    public void reinitialiser() {
        afficherMainJoueur();
    }
}
