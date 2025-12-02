package blackjack.vue;       
import blackjack.controleur.ControleurBlackjack;
import java.awt.*;
import javax.swing.*;




public class MisePanel extends JPanel{
    private  JLabel lblMise;
    private JTextField champMise;
    private JButton btnMiser;
    private ControleurBlackjack controleur;
    
    public MisePanel(){
        super(new FlowLayout(FlowLayout.LEFT));
        this.controleur = null;
        this.lblMise = new JLabel("Mise : ");
        this.champMise = new JTextField("100", 8);
        this.btnMiser = new JButton("Placer mise");
        this.btnMiser.addActionListener(e -> {
            if (controleur != null) {
                try {
                    int mise = Integer.parseInt(champMise.getText());
                    this.controleur.actionMiser(mise);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Veuillez entrer un nombre valide", 
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        this.add(lblMise);
        this.add(champMise);
        this.add(btnMiser);
    }
    
    public void setControleur(ControleurBlackjack controleur) {
        this.controleur = controleur;
    }
    
    
    public JTextField getChampMise() {
        return champMise;
    }
    
    public JButton getBtnMiser() {
        return btnMiser;
    }
}