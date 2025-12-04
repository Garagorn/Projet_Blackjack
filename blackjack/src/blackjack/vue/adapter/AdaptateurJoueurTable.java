package blackjack.vue.adapter;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;

/**
 * Adaptateur entre le ModeleBlackjack et le ModeleTableJoueurs.
 * Implémente le Pattern Adapter pour faire le pont entre 
 * la logique métier et l'affichage dans un JTable.
 */
public class AdaptateurJoueurTable {
    
    private ModeleBlackjack modele;
    private ModeleTableJoueurs modeleTable;
    
    /**
     * Constructeur de l'adaptateur
     * @param modele le modèle Blackjack
     */
    public AdaptateurJoueurTable(ModeleBlackjack modele) {
        this.modele = modele;
        this.modeleTable = new ModeleTableJoueurs();
        initialiser();
    }
    
    /**
     * Initialise le modèle de table avec les joueurs actuels
     */
    private void initialiser() {
        modeleTable.setJoueurs(modele.getJoueurs());
    }
    
    /**
     * Retourne le modèle de table pour l'attacher au JTable
     * @return le TableModel
     */
    public ModeleTableJoueurs getModeleTable() {
        return modeleTable;
    }
    
    /**
     * Actualise les données de la table depuis le modèle Blackjack
     */
    public void actualiserDonnees() {
        modeleTable.setJoueurs(modele.getJoueurs());
    }
    
    /**
     * Ajoute un joueur au modèle et à la table
     * @param joueur le joueur à ajouter
     */
    public void ajouterJoueur(Joueur joueur) {
        if (joueur != null) {
            modele.ajouterJoueur(joueur);
            modeleTable.ajouterJoueur(joueur);
        }
    }
    
    /**
     * Retire un joueur du modèle et de la table
     * @param joueur le joueur à retirer
     */
    public void retirerJoueur(Joueur joueur) {
        if (joueur != null) {
            modele.retirerJoueur(joueur);
            modeleTable.retirerJoueur(joueur);
        }
    }
    
    /**
     * Actualise uniquement l'affichage de la table sans recharger les données
     */
    public void rafraichirAffichage() {
        modeleTable.actualiser();
    }
    
    /**
     * Retourne le joueur sélectionné dans la table
     * @param selectedRow l'index de la ligne sélectionnée
     * @return le joueur correspondant
     */
    public Joueur getJoueurSelectionne(int selectedRow) {
        return modeleTable.getJoueurAt(selectedRow);
    }
    
    /**
     * Vide la table
     */
    public void vider() {
        modeleTable.vider();
    }
}