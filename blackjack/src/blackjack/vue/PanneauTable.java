package blackjack.vue;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import javax.swing.*;
import java.awt.*;
//import java.util.ArrayList;

public class PanneauTable extends JPanel {
    
    private PiochePanel panneauPioche;
    //private ArrayList<JoueurPanel> panneauxJoueurs;
    private CroupierPanel panneauCroupier;
    private ModeleBlackjack modele;
    private ConteneurJoueursPanel panneauJoueursContainer;  
    
    public PanneauTable(ModeleBlackjack modele) {
        this.modele = modele;
        //this.panneauxJoueurs = new ArrayList<>();
        this.panneauPioche = new PiochePanel(modele.getPioche());
        this.panneauCroupier = new CroupierPanel(modele.getCroupier());
        
        initialiserComposants();
        mettreAJourPanneauxJoueurs();
    }
    
    private void initialiserComposants() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0, 100, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Utilisation du conteneur pour les joueurs
        panneauJoueursContainer = new ConteneurJoueursPanel();  
        
        add(panneauCroupier, BorderLayout.NORTH);
        add(panneauJoueursContainer, BorderLayout.CENTER);
        add(panneauPioche, BorderLayout.EAST);
    }
    
    private void mettreAJourPanneauxJoueurs() {
        panneauJoueursContainer.removeAll();
        //panneauxJoueurs.clear();
        
        for (Joueur joueur : modele.getJoueurs()) {
            JoueurPanel joueurPanel = new JoueurPanel(joueur);
            //panneauxJoueurs.add(joueurPanel);
            panneauJoueursContainer.add(joueurPanel);
        }
        
        // Rafraîchir l'affichage
        panneauJoueursContainer.revalidate();
        panneauJoueursContainer.repaint();
    }
    
    public void afficherPaquets() {
        panneauPioche.afficherPioche();
        panneauCroupier.afficherMainCroupier();
        //for (JoueurPanel panneauJoueur : panneauxJoueurs) {
           // panneauJoueur.afficherMainJoueur();
        //}
    }
    
    public void actualiser() {
        mettreAJourPanneauxJoueurs();
        afficherPaquets();
    }
    
     
    
    public void reinitialiser() {
        // Déléguer la réinitialisation aux sous-panneaux
        panneauCroupier.reinitialiser();
        panneauPioche.reinitialiser();
    }
}
