package blackjack.vue;
import java.awt.*;
import javax.swing.*;
import modele.cartes.Paquet;

public class PiochePanel extends JPanel {
    
    private VuePioche piocheVue;
    private Paquet pioche;
    
    public PiochePanel(Paquet pioche){
        super();
        this.pioche = pioche;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(0, 100, 0));
        this.setBorder(BorderFactory.createTitledBorder("Pioche"));
        
        afficherPioche();
    }
    
    public void afficherPioche(){
        if (piocheVue != null) {
            this.remove(piocheVue);
        }
        
        piocheVue = new VuePioche(pioche);
        this.add(piocheVue, BorderLayout.CENTER);
        
        // Ajouter un label avec le nombre de cartes restantes
        JLabel lblInfo = new JLabel("Cartes: " + pioche.getPaquet().size(), SwingConstants.CENTER);
        lblInfo.setForeground(Color.WHITE);
        this.add(lblInfo, BorderLayout.SOUTH);
        
        this.revalidate();
        this.repaint();
    }
    
    public void reinitialiser() {
        afficherPioche();
    }
}