package blackjack.vue;
import modele.cartes.Paquet;
import modele.cartes.Carte;
import vue.VuePaquet;
import javax.swing.*;
import java.awt.*;

public class VueMainCroupier extends VuePaquet {
    
    private boolean revelerDeuxiemeCarte;
    
    public VueMainCroupier(Paquet mainCroupier, boolean revelerDeuxiemeCarte){
        super(mainCroupier);
        this.revelerDeuxiemeCarte = revelerDeuxiemeCarte;
        this.setBackground(new Color(0, 100, 0));
        afficherPaquet();
    }

    @Override
    public void afficherPaquet() {
        this.removeAll();
        
        if (getPaquet() == null || getPaquet().getPaquet().isEmpty()) {
            JLabel lblVide = new JLabel("Main vide", SwingConstants.CENTER);
            lblVide.setForeground(Color.WHITE);
            this.add(lblVide);
        } else {
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            
            java.util.List<Carte> cartes = getPaquet().getPaquet();
            for (int i = 0; i < cartes.size(); i++) {
                Carte carte = cartes.get(i);
                boolean visible = (i == 0) || revelerDeuxiemeCarte;  
                
                VueCarteBlackjack panelCarte =  new VueCarteBlackjack(carte, visible);
                this.add(panelCarte);
            }
        }
        
        this.revalidate();
        this.repaint();
    }

  

    public void setRevelerDeuxiemeCarte(boolean reveler){
        this.revelerDeuxiemeCarte = reveler;
        afficherPaquet();
    }

    public void modeleMiseAJour(Object o) {
        afficherPaquet();
    }
}