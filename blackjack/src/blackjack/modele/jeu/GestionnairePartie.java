package blackjack.modele.jeu;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import blackjack.modele.util.EtatPartie;
import java.util.ArrayList;

/**
 * Gestionnaire de la logique d'une partie de Blackjack.
 * 
 * RESPONSABILITÉS :
 * - Gérer le déroulement d'une partie (états)
 * - Distribuer les cartes initiales
 * - Gérer le tour des joueurs et du croupier
 * - Calculer les gains et pertes
 * - Gérer la défausse à la fin de la partie
 *
 */
public class GestionnairePartie {
    
    private ArrayList<Joueur> joueurs;
    private GestionnairePaquet gestionnairePaquet;
    private Croupier croupier;
    private EtatPartie etatPartie;
    private int joueurActifIndex;
    
    /**
     * Constructeur du gestionnaire de partie.
     * 
     * @param joueurs Liste des joueurs
     * @param gestionnairePaquet Gestionnaire de la pioche et défausse
     * @param croupier Le croupier
     */
    public GestionnairePartie(ArrayList<Joueur> joueurs, 
                              GestionnairePaquet gestionnairePaquet, 
                             Croupier croupier) {
        this.joueurs = joueurs;
        this.gestionnairePaquet = gestionnairePaquet;
        this.croupier = croupier;
        this.etatPartie = EtatPartie.EN_ATTENTE;
        this.joueurActifIndex = 0;
    }
    
    /**
     * Démarre une nouvelle partie.
     * 
     * ÉTAPES :
     * 1. Vérifier qu'il y a des joueurs
     * 2. Vérifier s'il faut remélanger la pioche
     * 3. Distribuer les cartes initiales
     * 4. Passer à l'état TOUR_JOUEURS
     */
    public void demarrerPartie() {
        if (joueurs.isEmpty()) {
            throw new IllegalStateException("Aucun joueur dans la partie");
        }
        
        // Vérifier s'il faut remélanger AVANT de commencer la distribution
        if (gestionnairePaquet.doitRemelanger(joueurs.size())) {
            gestionnairePaquet.remelanger();
        }
        
        etatPartie = EtatPartie.DISTRIBUTION;
        distribuerCartesInitiales();
        etatPartie = EtatPartie.TOUR_JOUEURS;
        joueurActifIndex = 0;
    }
    
    /**
     * Réinitialise la partie pour en commencer une nouvelle.
     * 
     * ÉTAPES :
     * 1. Envoyer toutes les cartes dans la défausse
     * 2. Réinitialiser les mains
     * 3. Retourner à l'état EN_ATTENTE
     */
    public void resetPartie() {
        etatPartie = EtatPartie.EN_ATTENTE;
        joueurActifIndex = 0;
        
        //Défausser avant de réinitialiser
        defausserToutesLesCartes();
        
        // Réinitialiser les mains
        reinitialiserPartie();
    }
    
    /**
     * Envoie toutes les cartes des joueurs et du croupier dans la défausse.
     * 
     * APPELÉ À LA FIN DE CHAQUE PARTIE.
     * C'est ici que la défausse se remplit !
     */
    private void defausserToutesLesCartes() {
        // Défausser les cartes du croupier
        if (croupier.getMain() != null && !croupier.getMain().estVide()) {
            gestionnairePaquet.defausserMain(croupier.getMain().getPaquetMain());
        }
        
        // Défausser les cartes de tous les joueurs
        for (Joueur joueur : joueurs) {
            for (Main main : joueur.getMains()) {
                if (!main.estVide()) {
                    gestionnairePaquet.defausserMain(main.getPaquetMain());
                }
            }
        }
    }
    
    /**
     * Réinitialise les mains des joueurs et du croupier.
     */
    private void reinitialiserPartie() {
        croupier.cacherDeuxiemeCarte();
        croupier.reinitialiserMain();
        
        for (Joueur joueur : joueurs) {
            joueur.reinitialiserMains();
            joueur.reinitialiserBilanPartie();
        }
    }
    
    /**
     * Distribue les cartes initiales.
     * 
     * ORDRE DE DISTRIBUTION :
     * 1. Une carte à chaque joueur
     * 2. Une carte au croupier (visible)
     * 3. Une deuxième carte à chaque joueur
     * 4. Une deuxième carte au croupier (cachée)
     */
    private void distribuerCartesInitiales() {
        // Premier tour : une carte à chaque joueur
        for (Joueur joueur : joueurs) {
            gestionnairePaquet.distribuerCarteJoueurs(joueur);
        }
        
        // Une carte au croupier (visible)
        gestionnairePaquet.distribuerCarteCroupier(croupier);
        
        // Deuxième tour : une deuxième carte à chaque joueur
        for (Joueur joueur : joueurs) {
            gestionnairePaquet.distribuerCarteJoueurs(joueur);
        }
        
        // Deuxième carte au croupier (cachée)
        gestionnairePaquet.distribuerCarteCroupier(croupier);
    }
    
    /**
     * Exécute une action pour le joueur actif.
     * 
     * @param action L'action à exécuter
     */
    public void executerAction(Action action) {
        if (etatPartie != EtatPartie.TOUR_JOUEURS) {
            return;
        }
        
        Joueur joueurActif = getJoueurActif();
        if (joueurActif == null) {
            return;
        }
        
        action.executer(joueurActif);
    }
    
    /**
     * Passe au joueur suivant.
     * 
     * LOGIQUE :
     * 1. Si le joueur actif a plusieurs mains, passer à la main suivante
     * 2. Sinon, passer au joueur suivant
     * 3. Si tous les joueurs ont joué, retourner true pour passer au croupier
     * 
     * @return true si tous les joueurs ont joué, false sinon
     */
    public boolean passerAuJoueurSuivant() {
        Joueur joueurActif = getJoueurActif();
        
        // Vérifier si le joueur a une deuxième main
        if (joueurActif != null && joueurActif.aUneDeuxiemeMain()) {
            joueurActif.passerAMainSuivante();
            return false; // Le même joueur continue avec sa 2ème main
        }
        
        // Passer au joueur suivant
        joueurActifIndex++;
        
        // Vérifier si tous les joueurs ont joué
        return joueurActifIndex >= joueurs.size();
    }
    
    /**
     * Vérifie si le joueur actif a déjà un blackjack.
     * 
     * @return true si le joueur actif a un blackjack, false sinon
     */
    public boolean joueurActifADejaBlackjack() {
        Joueur joueurActif = getJoueurActif();
        return joueurActif != null && joueurActif.aBlackjack();
    }
    
    /**
     * Gère le tour du croupier.
     * 
     * ÉTAPES :
     * 1. Révéler la deuxième carte
     * 2. Le croupier tire jusqu'à 17+
     * 3. Calculer les gains
     * 4. Passer à l'état TERMINE
     */
    public void tourCroupier() {
        etatPartie = EtatPartie.TOUR_CROUPIER;
        croupier.afficherDeuxiemeCarte();
        
        // Vérifier si au moins un joueur est encore en jeu
        boolean joueurEnVie = joueurs.stream()
            .anyMatch(j -> j.getMains().stream()
                .anyMatch(m -> !m.estBuste()));
        
        if (joueurEnVie) {
            croupier.jouer(gestionnairePaquet.getPaquet());
        }
        
        etatPartie = EtatPartie.TERMINE;
        determinerGagnants();
    }
    
    /**
     * Détermine les gagnants et calcule les gains.
     */
    private void determinerGagnants() {
        for (Joueur joueur : joueurs) {
            for (Main main : joueur.getMains()) {
                calculerGainsMain(main, joueur, croupier);
            }
        }
    }
    
    /**
     * Calcule les gains pour une main spécifique.
     * 
     * RÈGLES DE PAIEMENT :
     * - Blackjack du joueur (sans blackjack du croupier) : 3:2 (1.5x la mise)
     * - Victoire normale : 1:1 (mise doublée)
     * - Égalité : remboursement (récupère sa mise)
     * - Défaite : perte de la mise
     * 
     * @param main La main du joueur
     * @param joueur Le joueur
     * @param croupier Le croupier
     */
    private void calculerGainsMain(Main main, Joueur joueur, Croupier croupier) {
        int scoreJoueur = main.getScore();
        boolean joueurBlackjack = main.estBlackjack();
        boolean joueurBuste = main.estBuste();
        int mise = main.getMise();
        int scoreCroupier = croupier.getScore();
        boolean croupierBlackjack = croupier.aBlackjack();
        boolean croupierBuste = croupier.estBuste();

        // Si le joueur est buste, il perd tout (pas de gains)
        if (joueurBuste) {
            return;
        }

        // Si le croupier est buste, le joueur gagne
        if (croupierBuste) {
            if (joueurBlackjack) {
                // Blackjack paie 3:2
                joueur.crediterJetons(mise + (int)(mise * 1.5));
            } else {
                // Victoire normale : mise doublée
                joueur.crediterJetons(mise * 2);
            }
            return;
        }

        // Si le joueur a un blackjack mais pas le croupier
        if (joueurBlackjack && !croupierBlackjack) {
            joueur.crediterJetons(mise + (int)(mise * 1.5));
            return;
        }

        // Si le croupier a un blackjack mais pas le joueur
        if (croupierBlackjack && !joueurBlackjack) {
            // Le joueur perd (sauf s'il a pris une assurance)
            if (main.estAssure()) {
                int coutAssurance = mise / 2;
                joueur.crediterJetons(coutAssurance * 2);
            }
            return;
        }

        // Comparaison des scores
        if (scoreJoueur > scoreCroupier) {
            // Le joueur gagne
            joueur.crediterJetons(mise * 2);
        } else if (scoreJoueur == scoreCroupier) {
            // Égalité : remboursement
            joueur.crediterJetons(mise);
        }
        // Sinon, le joueur perd (pas de crédits)
    }
    
    // ===== GETTERS =====
    /**
     * Getter de la défausse.
     * 
     * @return le joueur actif
     */
    public Joueur getJoueurActif() {
        if (joueurActifIndex >= 0 && joueurActifIndex < joueurs.size()) {
            return joueurs.get(joueurActifIndex);
        }
        return null;
    }
    
    /**
     * Getter de la défausse.
     * 
     * @return L'indice du joueur actif
     */
    public int getJoueurActifIndex() {
        return joueurActifIndex;
    }
    
    /**
     * Getter de la défausse.
     * T
     * @return L'état de la partie
     */
    public EtatPartie getEtatPartie() {
        return etatPartie;
    }
}