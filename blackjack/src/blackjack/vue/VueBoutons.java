package blackjack.vue;

import blackjack.controleur.ControleurJeu;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;



public class VueBoutons extends JPanel{
    
    private JButton boutonTirer, boutonRester, boutonDoubler, boutonDiviser,  boutonAssurance;
    public VueBoutons(){
       
    boutonTirer = new JButton("Tirer");
    boutonRester = new JButton("Rester");
    boutonDoubler = new JButton("Doubler");
    boutonDiviser = new JButton("Diviser");
    boutonAssurance = new JButton("Assurance");
    setLayout(new GridLayout(5, 1));
    this.add(boutonTirer);
    this.add(boutonRester);
    this.add(boutonDoubler);
    this.add(boutonDiviser);
    this.add(boutonAssurance);
             
    }
    
    public void setControleur(ControleurJeu controleur){
    
    boutonTirer.addActionListener(controleur);
    boutonRester.addActionListener(controleur);
    boutonDoubler.addActionListener(controleur);
    boutonDiviser.addActionListener(controleur);
    boutonAssurance.addActionListener(controleur);
    
    
    }


}
