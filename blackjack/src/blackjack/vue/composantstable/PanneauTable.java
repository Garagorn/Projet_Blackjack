package blackjack.vue.composantstable;
import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import javax.swing.*;
import java.awt.*;

/**
 * Panneau principal de la table de blackjack.
 * Contient tous les éléments visuels de la table : pioche, défausse, croupier et joueurs.
 * Organise l'affichage selon le layout BorderLayout.
 */
public class PanneauTable extends JPanel {
    
    /**
     *panneau qui affiche la pioche
     */
    private PiochePanel panneauPioche;
    
    /**
     *panneau qui affiche la defausse 
     */
    private DefaussePanel panneauDefausse;
    
    /**
     *panneau qui affiche la main du croupier , son score ..
     */
    private CroupierPanel panneauCroupier;
    /**
     *reference sur le modele principale
     */
    private ModeleBlackjack modele;
    
    /**
     *panneau qui contient tout et affiche tout les joueurs avec leurs mains leurs scores..
     */
    private ConteneurJoueursPanel panneauJoueursContainer;  
    
    /**
     * Constructeur du panneau de table.
     * Initialise tous les composants et organise leur disposition.
     * 
     * @param modele le modèle de blackjack contenant les données du jeu
     */
    public PanneauTable(ModeleBlackjack modele) {
        this.modele = modele;
        this.panneauPioche = new PiochePanel(modele.getPioche());
        this.panneauDefausse = new DefaussePanel(modele.getDefausse());
        this.panneauCroupier = new CroupierPanel(modele.getCroupier());
        
        initialiserComposants();
        mettreAJourPanneauxJoueurs();
    }
    
    /**
     * Initialise les composants graphiques et leur disposition.
     * Utilise BorderLayout pour organiser les éléments :
     * - Croupier en haut
     * - Joueurs au centre
     * - Pioche à droite
     * - Défausse en bas à gauche
     */
    private void initialiserComposants() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0, 100, 0)); // Vert de la table
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panneauJoueursContainer = new ConteneurJoueursPanel();  
        
        // Configuration du layout principal
        add(panneauCroupier, BorderLayout.NORTH);
        add(panneauJoueursContainer, BorderLayout.CENTER);
        add(panneauPioche, BorderLayout.EAST);
        
        // Modification : Défausse en bas à gauche (plus petit, moins intrusif)
        JPanel panneauBasGauche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauBasGauche.setBackground(new Color(0, 100, 0));
        panneauBasGauche.setPreferredSize(new Dimension(150, 200)); // Taille réduite
        
        JPanel defausseContainer = new JPanel(new BorderLayout());
        defausseContainer.setPreferredSize(new Dimension(120, 190));
        defausseContainer.add(panneauDefausse, BorderLayout.CENTER);
        
        panneauBasGauche.add(defausseContainer);
        add(panneauBasGauche, BorderLayout.SOUTH);
    }
    
    /**
     * Met à jour les panneaux des joueurs.
     * Crée un nouveau panneau pour chaque joueur présent dans le modèle.
     */
    private void mettreAJourPanneauxJoueurs() {
        panneauJoueursContainer.removeAll();
        for (Joueur joueur : modele.getJoueurs()) {
            JoueurPanel joueurPanel = new JoueurPanel(joueur);
            panneauJoueursContainer.add(joueurPanel);
        }
        
        panneauJoueursContainer.revalidate();
        panneauJoueursContainer.repaint();
    }
    
    /**
     * Affiche ou met à jour l'affichage des paquets (pioche et main du croupier).
     */
    public void afficherPaquets() {
        panneauPioche.removeAll();
        panneauDefausse.removeAll();
        panneauPioche.afficherPioche();
        panneauDefausse.afficherDefausse();
        panneauCroupier.afficherMainCroupier();
    }
    
    /**
     * Actualise l'ensemble de l'affichage de la table.
     * Met à jour les joueurs et les paquets.
     */
    public void actualiser() {
        mettreAJourPanneauxJoueurs();
        afficherPaquets();
    }
    
    /**
     * Réinitialise l'affichage de la table.
     * Méthode appelée lors d'une nouvelle partie.
     */
    public void reinitialiser() {
        panneauCroupier.reinitialiser();
        panneauPioche.reinitialiser();
    }
}