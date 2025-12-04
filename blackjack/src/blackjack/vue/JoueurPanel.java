package blackjack.vue;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import java.awt.*;
import javax.swing.*;
import modele.cartes.Paquet;

public class JoueurPanel extends JPanel {
    
    private final JLabel lblJoueur;
    private MainsPanel mainsConteneur;
    private VueMainJoueur mainJoueurVue;
    private Joueur joueur;
    
    public JoueurPanel(Joueur joueur){
        super();
        this.joueur = joueur;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0, 100, 0));
        this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        this.mainsConteneur = new MainsPanel();
        this.add(mainsConteneur, BorderLayout.CENTER);
        lblJoueur = new JLabel("Joueur", SwingConstants.CENTER);
        lblJoueur.setFont(new Font("Arial", Font.BOLD, 16));
        lblJoueur.setForeground(Color.WHITE);
        this.add(lblJoueur, BorderLayout.NORTH);
        
        afficherMainJoueur();
    }

    public void afficherMainJoueur(){
         mainsConteneur.removeAll();
         
        if (this.joueur == null || this.joueur.getMains() == null || this.joueur.getMains().isEmpty()) {
            JLabel lblVide = new JLabel("Aucune main", SwingConstants.CENTER);
            lblVide.setForeground(Color.WHITE);
            mainsConteneur.add(lblVide);
  
        } else {
            for (int i = 0; i < joueur.getMains().size(); i++) {
                Main main = joueur.getMains().get(i);
                VueMainJoueur vueMain = new VueMainJoueur(main.getPaquetMain());
                MainPanel mainPanneau = new MainPanel();
                
                String infoMain = "Main " + (i + 1);
                if (i == joueur.getMainActuelleIndex()) {
                    infoMain += " (ACTUELLE)";
                    mainPanneau.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                  
                }
                
                JLabel lblMain = new JLabel(infoMain, SwingConstants.CENTER);
                lblMain.setForeground(Color.WHITE);
                mainPanneau.add(lblMain, BorderLayout.NORTH);
                mainPanneau.add(vueMain, BorderLayout.CENTER);
                
                mainsConteneur.add(mainPanneau);
            }
          
            String info = this.joueur.getNom() + " - Score: " + joueur.getScore();
            if (joueur.getMainActuelle().estBuste()) {
                info += " (BUSTE!)";
            } else if (joueur.getMainActuelle().estBlackjack()) {
                info += " (BLACKJACK!)";
            }
            lblJoueur.setText(info);
        }
        
        this.revalidate();
        this.repaint();
    }
    
    public void reinitialiser() {
        afficherMainJoueur();
    }
}