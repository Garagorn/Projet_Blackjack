package blackjack.vue.adapter;

import blackjack.modele.event.EcouteurModele;
import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurIA;
import javax.swing.table.AbstractTableModel;

/**
 * Adaptateur qui adapte le ModeleBlackjack à l'interface AbstractTableModel de Swing.
 * Implémente le pattern Adapter ET le pattern Observer en écoutant les changements du modèle.
 * 
 * Cette classe adapte l'interface du modèle métier (ModeleBlackjack)
 * vers l'interface attendue par la JTable (AbstractTableModel), et se met à jour
 * automatiquement lorsque le modèle change.
 */
public class ModeleTableJoueurs extends AbstractTableModel implements EcouteurModele {
    
    private static final String[] NOMS_COLONNES = {
        "Joueur", "Type", "Jetons", "Mise", "Score", "Gains/Pertes (Total)"
    };
    
    /** Référence vers le modèle métier - c'est l'objet qu'on adapte */
    private ModeleBlackjack modele;
    
    /**
     * Constructeur de l'adaptateur.
     * S'enregistre automatiquement comme écouteur du modèle.
     * 
     * @param modele Le modèle métier à adapter pour l'affichage en table.
     */
    public ModeleTableJoueurs(ModeleBlackjack modele) {
        this.modele = modele;
        // S'enregistre comme écouteur pour être notifié des changements
        this.modele.ajouterEcouteur(this);
    }
    
    /**
     * Méthode appelée automatiquement par le modèle lorsqu'il change.
     * Met à jour l'affichage de la table.
     * 
     * @param source Le modèle qui a été mis à jour.
     */
    @Override
    public void modeleMiseAJour(Object source) {
        fireTableDataChanged();
    }
    
    /**
     * Méthode de nettoyage pour se désabonner du modèle.
     * À appeler si on veut détruire cet adaptateur proprement.
     */
    public void detacher() {
        modele.retirerEcouteur(this);
    }
    
    @Override
    public int getRowCount() {
        return modele.getJoueurs().size();
    }
    
    @Override
    public int getColumnCount() {
        return NOMS_COLONNES.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return NOMS_COLONNES[column];
    }
    
    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0: // Nom du joueur
            case 1: // Type (Humain/IA)
                return String.class;
            case 2: // Jetons
            case 3: // Mise
            case 4: // Score
            case 5: // Gains/Pertes
                return Integer.class;
            default:
                return Object.class;
        }
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        if (row < 0 || row >= modele.getJoueurs().size()) {
            return null;
        }
        
        Joueur joueur = modele.getJoueurs().get(row);
        
        switch (column) {
            case 0: // Nom du joueur
                return joueur.getNom();
                
            case 1: // Type du joueur
                if (joueur.estHumain()) {
                    return "Humain";
                } else {
                    JoueurIA joueurIA = (JoueurIA) joueur;
                    return "IA " + joueurIA.getStrategie().getNom();
                }
                
            case 2: // Jetons
                return joueur.getJetons();
                
            case 3: // Mise
                return joueur.getMiseActuelle();
                
            case 4: // Score
                return joueur.getScore();
                
            case 5: // Gains/Pertes
                return joueur.getBilanSession();
                
            default:
                return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    /**
     * Retourne le joueur à une ligne donnée.
     * Méthode utilitaire pour récupérer le joueur sélectionné dans la table.
     * 
     * @param row L'index de la ligne.
     * @return Le joueur correspondant à la ligne.
     */
    public Joueur getJoueurAt(int row) {
        if (row >= 0 && row < modele.getJoueurs().size()) {
            return modele.getJoueurs().get(row);
        }
        return null;
    }
}