package blackjack.vue;

import blackjack.modele.joueurs.Joueur;
import javax.swing.*;
import java.awt.*;

/**
 * Panneau affichant les informations du joueur actif.
 * Affiche le nom, les jetons, le score et des messages.
 */
public class PanneauInfosJoueur extends JPanel {
    
    private JLabel lblNom;
    private JLabel lblJetons;
    private JLabel lblScore;
    private JLabel lblMessage;
    
    /**
     * Constructeur du panneau d'informations
     */
    public PanneauInfosJoueur() {
        initialiserComposants();
    }
    
    /**
     * Initialise les composants du panneau
     */
    private void initialiserComposants() {
        setLayout(new GridLayout(4, 1, 5, 5));
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
     * Affiche les informations d'un joueur
     * @param joueur le joueur à afficher
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
        
        // Vérifier si blackjack ou brûlé
        if (joueur.getMainActuelle() != null) {
            if (joueur.getMainActuelle().estBlackjack()) {
                scoreText += " - BLACKJACK!";
                lblScore.setForeground(new Color(0, 128, 0));
            } else if (joueur.getMainActuelle().estBuste()) {
                scoreText += " - BRÛLÉ!";
                lblScore.setForeground(Color.RED);
            } else {
                lblScore.setForeground(Color.BLACK);
            }
        }
        
        lblScore.setText(scoreText);
    }
    
    /**
     * Affiche un message informatif
     * @param message le message à afficher
     */
    public void afficherMessage(String message) {
        lblMessage.setText(message);
    }
    
    /**
     * Efface le message
     */
    public void effacerMessage() {
        lblMessage.setText("");
    }
}