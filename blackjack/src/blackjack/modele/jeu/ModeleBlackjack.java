package blackjack.modele.jeu;

import blackjack.modele.actions.Action;
import blackjack.modele.actions.GestionnaireActions;
import blackjack.modele.event.AbstractModeleEcoutable;
import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import blackjack.modele.joueurs.JoueurIA;
import blackjack.modele.util.EtatPartie;
import modele.cartes.Carte;
import modele.cartes.Paquet;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle principal du jeu Blackjack - Version refactorisée
 */
public class ModeleBlackjack extends AbstractModeleEcoutable {
    
    private List<Joueur> joueurs;
    private Croupier croupier;
    private GestionnairePartie gestionnairePartie;
    private GestionnaireActions gestionnaireActions;
    private GestionnairePaquet gestionnairePaquet;
    private int miseMinimale;
    
    public ModeleBlackjack(int miseMinimale) {
        this.joueurs = new ArrayList<>();
        this.croupier = new Croupier();
        this.gestionnaireActions = new GestionnaireActions(this);
        this.gestionnairePaquet = new GestionnairePaquet();
        this.gestionnairePartie = new GestionnairePartie(this);
        this.miseMinimale = miseMinimale;
    }
    
    // ========== GESTION DES JOUEURS ==========
    
    public void ajouterJoueur(Joueur joueur) {
        if (joueur != null && !joueurs.contains(joueur)) {
            joueurs.add(joueur);
            fireChangement();
        }
    }
    
    public void retirerJoueur(Joueur joueur) {
        if (joueurs.remove(joueur)) {
            fireChangement();
        }
    }
    
    public Joueur getJoueurActif() {
        return gestionnairePartie.getJoueurActif();
    }
    
    public int getJoueurActifIndex() {
        return gestionnairePartie.getJoueurActifIndex();
    }
    
    public JoueurHumain getJoueurHumain() {
        return (JoueurHumain) joueurs.stream()
                .filter(j -> j instanceof JoueurHumain)
                .findFirst()
                .orElse(null);
    }
    
    // ========== GESTION DE LA PARTIE ==========
    
    public void demarrerPartie() {
        gestionnairePartie.demarrerPartie();
    }
    
    public void resetPartie() {
        gestionnairePartie.resetPartie();
    }
    
    public void passerAuJoueurSuivant() {
        gestionnairePartie.passerAuJoueurSuivant();
    }
    
    public void tourCroupier() {
        gestionnairePartie.tourCroupier();
    }
    
    public void executerAction(Action action) {
       gestionnairePartie.executerAction(action);
    }
    
    // ========== GETTERS ==========
    
    public List<Joueur> getJoueurs() {
        return new ArrayList<>(joueurs);
    }
    
    public Croupier getCroupier() {
        return croupier;
    }
    
    public Paquet getPioche() {
        return gestionnairePaquet.getPaquet();
    }
    
    public EtatPartie getEtatPartie() {
        return gestionnairePartie.getEtatPartie();
    }
    
    public int getMiseMinimale() {
        return miseMinimale;
    }
    
    public void setMiseMinimale(int miseMinimale) {
        if (miseMinimale > 0) {
            this.miseMinimale = miseMinimale;
        }
    }
    
    public GestionnaireActions getGestionnaireActions() {
        return gestionnaireActions;
    }
    
    public Carte getCarteVisibleCroupier() {
        return croupier.getCarteVisible();
    }
    
    public int getNombreJoueurs() {
        return joueurs.size();
    }
    
    /**
     * Retourne les actions disponibles pour un joueur
     * @param joueur le joueur
     * @return liste des actions possibles
     */
    public List<Action> getActionsDisponibles(Joueur joueur) {
        return gestionnaireActions.obtenirActionsExecutables(joueur);
    }
    
    // ========== METHODES INTERNES POUR LES GESTIONNAIRES ==========
    
    List<Joueur> getJoueursInternes() {
        return joueurs;
    }
    
    GestionnairePaquet getGestionnairePaquet() {
        return gestionnairePaquet;
    }
    
    @Override
    public String toString() {
        return "ModeleBlackjack [État: " + gestionnairePartie.getEtatPartie() + 
               ", Joueurs: " + joueurs.size() + "]";
    }
}