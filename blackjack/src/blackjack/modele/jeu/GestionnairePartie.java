package blackjack.modele.jeu;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import blackjack.modele.util.EtatPartie;
import java.util.List;

/**
 * Gestionnaire dédié à la logique de partie
 */
public class GestionnairePartie {
    private ModeleBlackjack modele;
    private EtatPartie etatPartie;
    private int joueurActifIndex;
    
    public GestionnairePartie(ModeleBlackjack modele) {
        this.modele = modele;
        this.etatPartie = EtatPartie.EN_ATTENTE;
        this.joueurActifIndex = 0;
    }
    
    public void demarrerPartie() {
        if (modele.getJoueursInternes().isEmpty()) {
            throw new IllegalStateException("Aucun joueur dans la partie");
        }
        
        etatPartie = EtatPartie.DISTRIBUTION;
        distribuerCartesInitiales();
        verifierBlackjacksNaturels();
        
        etatPartie = EtatPartie.TOUR_JOUEURS;
        joueurActifIndex = 0;
        
        modele.fireChangement();
    }
    
    public void resetPartie() {
        etatPartie = EtatPartie.EN_ATTENTE;
        joueurActifIndex = 0;
        reinitialiserPartie();
        modele.fireChangement();
    }
    
    private void reinitialiserPartie() {
        modele.getCroupier().cacherDeuxiemeCarte();
        modele.getCroupier().reinitialiserMain();
        
        for (Joueur joueur : modele.getJoueursInternes()) {
            joueur.reinitialiserMains();
        }
        if (modele.getGestionnairePaquet().doitRemelanger()) {
            modele.getGestionnairePaquet().remelanger();
        }
    }
    
    private void distribuerCartesInitiales() {
        for (Joueur joueur : modele.getJoueursInternes()) {
            modele.getGestionnairePaquet().distribuerCarteJoueurs(joueur);
        }
        
        modele.getGestionnairePaquet().distribuerCarteCroupier(modele.getCroupier());
        
        for (Joueur joueur : modele.getJoueursInternes()) {
            modele.getGestionnairePaquet().distribuerCarteJoueurs(joueur);
        }
       
        modele.getGestionnairePaquet().distribuerCarteCroupier(modele.getCroupier());
        
        modele.fireChangement();
    }
    
    private void verifierBlackjacksNaturels() {
        if (modele.getCroupier().aBlackjack()) {
            modele.getCroupier().afficherDeuxiemeCarte();
            etatPartie = EtatPartie.TERMINE;
            determinerGagnants();
        }
    }
    
    public void executerAction(Action action) {
        if (etatPartie != EtatPartie.TOUR_JOUEURS) {
            return;
        }
        
        Joueur joueurActif = getJoueurActif();
        if (joueurActif == null) {
             return;
        }
        action.executer(joueurActif);
        
         
            modele.fireChangement();
        
 
    }
    
    public void passerAuJoueurSuivant() {
        Joueur joueurActif = getJoueurActif();
        
        if (joueurActif != null && joueurActif.aUneDeuxiemeMain()) {
            joueurActif.passerAMainSuivante();
            modele.fireChangement();
            return;
        }
        
        joueurActifIndex++;
        
        if (joueurActifIndex >= modele.getJoueursInternes().size()) {
            tourCroupier();
        } else {
            modele.fireChangement();
        }
    }
    
    public void tourCroupier() {
        etatPartie = EtatPartie.TOUR_CROUPIER;
        modele.fireChangement();
        modele.getCroupier().afficherDeuxiemeCarte();
        boolean joueurEnVie = modele.getJoueursInternes().stream()
            .anyMatch(j -> j.getMains().stream()
                .anyMatch(m -> !m.estBuste()));
        
        if (joueurEnVie) {
            modele.getCroupier().jouer(modele.getPioche());
        }
        
        etatPartie = EtatPartie.TERMINE;
        determinerGagnants();
        modele.fireChangement();
    }
    
    private void determinerGagnants() {
        Croupier croupier = modele.getCroupier();

        for (Joueur joueur : modele.getJoueursInternes()) {
            for (Main main : joueur.getMains()) {
                calculerGainsMain(main,joueur, croupier);
            }
        }
    }
    
    private void calculerGainsMain(Main main, Joueur joueur, Croupier croupier) {
        int scoreJoueur = main.getScore();
        boolean joueurBlackjack = main.estBlackjack();
        boolean joueurBuste = main.estBuste();
        int mise = main.getMise();
        int scoreCroupier = croupier.getScore();
        boolean croupierBlackjack = croupier.aBlackjack();
        boolean croupierBuste = croupier.estBuste();

        if (!joueurBuste) {
            if (croupierBuste) {
                if (joueurBlackjack) {
                    joueur.crediterJetons(mise + (int)(mise * 1.5));
                } else {
                    joueur.crediterJetons(mise * 2);
                }
            } else if (joueurBlackjack && !croupierBlackjack) {
                joueur.crediterJetons(mise + (int)(mise * 1.5));
            } else if (croupierBlackjack && !joueurBlackjack) {
                if (main.estAssure()) {
                    int coutAssurance = mise / 2;
                    joueur.crediterJetons(coutAssurance * 2);
                }
            } else if (scoreJoueur > scoreCroupier) {
                joueur.crediterJetons(mise * 2);
            } else if (scoreJoueur == scoreCroupier) {
                joueur.crediterJetons(mise);
            }
        }
    }
    
    // ========== GETTERS ==========
    
    public Joueur getJoueurActif() {
        if (joueurActifIndex >= 0 && joueurActifIndex < modele.getJoueursInternes().size()) {
            return modele.getJoueursInternes().get(joueurActifIndex);
        }
        return null;
    }
    
    public int getJoueurActifIndex() {
        return joueurActifIndex;
    }
    
    public EtatPartie getEtatPartie() {
        return etatPartie;
    }
}