package blackjack.vue;

import blackjack.modele.joueurs.Joueur;
import javax.swing.*;
import java.awt.*;

/**
 * Panneau affichant les informations du joueur actif.
 * Ce panneau présente des informations telles que le nom du joueur, ses jetons, son score,
 * et des messages de statut (ex : Blackjack, Brûlé, etc.).
 */
public class PanneauInfosJoueur extends JPanel {
    /**
     *contient le nom du joueur
     */
    private JLabel lblNom;
    
    /**
     *pour afficher les jetons du joueur
     */
    private JLabel lblJetons;
    /**
     *pour afficher le score du joueur
     */
    private JLabel lblScore;
    /**
     *pour afficher des informations  concernant le joueur actif
     */
    private JLabel lblMessage;
    
    /**
     * Constructeur du panneau d'informations du joueur.
     * Initialise les composants visuels du panneau.
     */
    public PanneauInfosJoueur() {
        initialiserComposants();
    }
    
    /**
     * Initialise les composants visuels du panneau d'informations.
     * Crée les labels pour afficher le nom, les jetons, le score et les messages.
     */
    private void initialiserComposants() {
        setLayout(new GridLayout(4, 1, 5, 5));  // Disposition verticale avec 4 lignes
        setBorder(BorderFactory.createTitledBorder("Informations Joueur"));
        
        lblNom = new JLabel("Joueur : -");
        lblNom.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblJetons = new JLabel("Jetons : -");
        lblJetons.setFont(new Font("Arial", Font.PLAIN, 12));
        
        lblScore = new JLabel("Score : -");
        lblScore.setFont(new Font("Arial", Font.PLAIN, 12));
        
        lblMessage = new JLabel("");
        lblMessage.setFont(new Font("Arial", Font.ITALIC, 12));
        lblMessage.setForeground(Color.BLUE);
        
        add(lblNom);
        add(lblJetons);
        add(lblScore);
        add(lblMessage);
    }
    
    /**
     * Affiche les informations du joueur.
     * Met à jour les labels avec le nom, les jetons et le score du joueur.
     * Si le joueur a un Blackjack ou s'il est brûlé, un message spécifique est affiché.
     * 
     * @param joueur Le joueur dont les informations sont à afficher.
     */
    public void afficherInfos(Joueur joueur) {
        if (joueur == null) {
            lblNom.setText("Joueur : -");
            lblJetons.setText("Jetons : -");
            lblScore.setText("Score : -");
            lblMessage.setText("");
            return;
        }
        
        lblNom.setText("Joueur : " + joueur.getNom());
        lblJetons.setText("Jetons : " + joueur.getJetons() + " €");
        
        int score = joueur.getScore();
        String scoreText = "Score : " + score;
        
        // Vérification de l'état du joueur (Blackjack ou Brûlé)
        if (joueur.getMainActuelle() != null) {
            if (joueur.getMainActuelle().estBlackjack()) {
                scoreText += " - BLACKJACK!";
                lblScore.setForeground(new Color(0, 128, 0));  // Vert pour Blackjack
            } else if (joueur.getMainActuelle().estBuste()) {
                scoreText += " - BRÛLÉ!";
                lblScore.setForeground(Color.RED);  // Rouge pour Brûlé
            } else {
                lblScore.setForeground(Color.BLACK);  // Noir pour un score normal
            }
        }
        
        lblScore.setText(scoreText);
    }
    
    /**
     * Affiche un message informatif sous le score du joueur.
     * Ce message peut indiquer des informations supplémentaires, comme des conseils ou des alertes.
     *
     * @param message Le message à afficher.
     */
    public void afficherMessage(String message) {
        lblMessage.setText(message);
    }
    
    /**
     * Efface le message actuellement affiché sous le score du joueur.
     * Utilisé pour réinitialiser le message lorsque nécessaire.
     */
    public void effacerMessage() {
        lblMessage.setText("");
    }
}
