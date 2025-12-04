package blackjack.vue;
import blackjack.modele.joueurs.Croupier;
import java.awt.*;
import javax.swing.*;
import modele.cartes.Paquet;

public class CroupierPanel extends JPanel {
    
    private JLabel lblCroupier;
    private VueMainCroupier mainCroupierVue;
    private Croupier croupier;
    private boolean cartesRevelees;
    
    public CroupierPanel(Croupier croupier){
        super();
        this.croupier = croupier;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0, 100, 0));
        this.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        lblCroupier = new JLabel("Croupier", SwingConstants.CENTER);
        lblCroupier.setFont(new Font("Arial", Font.BOLD, 16));
        lblCroupier.setForeground(Color.WHITE);
        this.add(lblCroupier, BorderLayout.NORTH);
    }
    
    public void afficherMainCroupier(){
        this.cartesRevelees = croupier.getRevelerDeuxiemeCarte();
        if (mainCroupierVue != null) {
            this.remove(mainCroupierVue);
        }
        
        if (croupier == null || croupier.getMain() == null) {
            mainCroupierVue = new VueMainCroupier(new Paquet(), cartesRevelees);
        } else {
            Paquet mainCroupier = croupier.getMain().getPaquetMain();
            mainCroupierVue = new VueMainCroupier(mainCroupier, cartesRevelees);
            
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
        
        this.add(mainCroupierVue, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    
    
    public void reinitialiser() {
        afficherMainCroupier();
    }
}