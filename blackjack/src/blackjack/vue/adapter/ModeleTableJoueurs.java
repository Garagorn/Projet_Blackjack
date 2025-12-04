package blackjack.vue.adapter;

import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurIA;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle de table pour afficher les informations des joueurs dans un JTable.
 * Permet de suivre en temps réel les jetons, mises, scores et gains de chaque joueur.
 */
public class ModeleTableJoueurs extends AbstractTableModel {
    
    private static final String[] NOMS_COLONNES = {
        "Joueur", "Type", "Jetons", "Mise", "Score", "Gains/Pertes"
    };
    
    private List<Joueur> joueurs;
    
    /**
     * Constructeur du modèle de table
     */
    public ModeleTableJoueurs() {
        this.joueurs = new ArrayList<>();
    }
    
    /**
     * Définit la liste des joueurs à afficher
     * @param joueurs la liste des joueurs
     */
    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = new ArrayList<>(joueurs);
        fireTableDataChanged();
    }
    
    /**
     * Ajoute un joueur à la table
     * @param joueur le joueur à ajouter
     */
    public void ajouterJoueur(Joueur joueur) {
        if (joueur != null && !joueurs.contains(joueur)) {
            joueurs.add(joueur);
            fireTableRowsInserted(joueurs.size() - 1, joueurs.size() - 1);
        }
    }
    
    /**
     * Retire un joueur de la table
     * @param joueur le joueur à retirer
     */
    public void retirerJoueur(Joueur joueur) {
        int index = joueurs.indexOf(joueur);
        if (index >= 0) {
            joueurs.remove(index);
            fireTableRowsDeleted(index, index);
        }
    }
    
    /**
     * Actualise l'affichage de la table
     */
    public void actualiser() {
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return joueurs.size();
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
            case 0: // Joueur
            case 1: // Type
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
        if (row < 0 || row >= joueurs.size()) {
            return null;
        }
        
        Joueur joueur = joueurs.get(row);
        
        switch (column) {
            case 0: // Nom du joueur
                return joueur.getNom();
                
            case 1: // Type (Humain/IA)
                if (joueur.estHumain()) {
                    return "Humain";
                } else {
                    JoueurIA joueurIA = (JoueurIA) joueur;
                    String strategie =  joueurIA.getStrategie() != null ? 
                        joueurIA.getStrategie().getNom() : "IA";
                    return strategie;
                }
                
            case 2: // Jetons actuels
                return joueur.getJetons();
                
            case 3: // Mise actuelle
                return joueur.getMiseActuelle();
                
            case 4: // Score de la main actuelle
                return joueur.getScore();
                
            case 5: // Bilan de la session (gains - pertes)
                int bilan = joueur.getBilanSession();
                return bilan;
                
            default:
                return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        // Aucune cellule n'est éditable
        return false;
    }
    
    /**
     * Retourne le joueur à une ligne donnée
     * @param row l'index de la ligne
     * @return le joueur correspondant
     */
    public Joueur getJoueurAt(int row) {
        if (row >= 0 && row < joueurs.size()) {
            return joueurs.get(row);
        }
        return null;
    }
    
    /**
     * Vide la table
     */
    public void vider() {
        joueurs.clear();
        fireTableDataChanged();
    }
}