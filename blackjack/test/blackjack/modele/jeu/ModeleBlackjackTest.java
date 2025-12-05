package blackjack.modele.jeu;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import blackjack.modele.joueurs.JoueurIA;
import blackjack.modele.strategie.StrategieJeu;
import blackjack.modele.util.EtatPartie;
import modele.cartes.Carte;
import modele.cartes.Paquet;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class ModeleBlackjackTest {
    
    private ModeleBlackjack modele;
    
    // Mock de test pour le joueurIA
    private static class StrategieMock implements StrategieJeu {
        @Override
        public Action choisirAction(blackjack.modele.joueurs.Main main, 
                                    Carte carteVisibleCroupier, 
                                    List<Action> actionsDisponibles) {
            return actionsDisponibles.isEmpty() ? null : actionsDisponibles.get(0);
        }
        
        @Override
        public String getNom() {
            return "Mock";
        }
    }
    
    @Before
    public void setUp() {
        modele = new ModeleBlackjack(10);
    }
    
    @Test
    public void testConstructeur() {
        assertEquals(10, modele.getMiseMinimale());
        assertNotNull(modele.getCroupier());
        assertNotNull(modele.getPioche());
        assertNotNull(modele.getJoueurs());
        assertEquals(0, modele.getNombreJoueurs());
        assertEquals(EtatPartie.EN_ATTENTE, modele.getEtatPartie());
    }
 

/**
 * Testssur les joueurs
 */
    @Test
    public void testAjouterJoueur() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        
        assertEquals(1, modele.getNombreJoueurs());
        assertTrue(modele.getJoueurs().contains(joueur));
    }
    @Test
    public void testAjouterJoueurNull() {
        modele.ajouterJoueur(null);
        assertEquals(0, modele.getNombreJoueurs());
    }
    @Test
    public void testAjouterJoueurDeuxFois() {
        Joueur joueur = new JoueurHumain("Alice", 1000);        
        modele.ajouterJoueur(joueur);
        modele.ajouterJoueur(joueur);
        
        assertEquals(1, modele.getNombreJoueurs());
    }
    @Test
    public void testAjouterPlusieursJoueurs() {
        Joueur joueur1 = new JoueurHumain("Alice", 1000);
        Joueur joueur2 = new JoueurHumain("Bob", 1000);
        Joueur joueur3 = new JoueurIA("Robot", 1000, new StrategieMock());
        
        modele.ajouterJoueur(joueur1);
        modele.ajouterJoueur(joueur2);
        modele.ajouterJoueur(joueur3);
        
        assertEquals(3, modele.getNombreJoueurs());
    }
    @Test
    public void testRetirerJoueur() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);        
        modele.retirerJoueur(joueur);
        
        assertEquals(0, modele.getNombreJoueurs());
        assertFalse(modele.getJoueurs().contains(joueur));
    } 
    @Test
    public void testRetirerJoueurInexistant() {
        Joueur joueur1 = new JoueurHumain("Alice", 1000);
        Joueur joueur2 = new JoueurHumain("Bob", 1000);
        
        modele.ajouterJoueur(joueur1);
        modele.retirerJoueur(joueur2);
        
        assertEquals(1, modele.getNombreJoueurs());
    }
   
    

/**
 * Tests de get de joueur
 */    
    @Test
    public void testGetJoueurHumain() {
        JoueurHumain humain = new JoueurHumain("Alice", 1000);
        JoueurIA ia = new JoueurIA("Robot", 1000, new StrategieMock());
        
        modele.ajouterJoueur(humain);
        modele.ajouterJoueur(ia);
        
        assertEquals(humain, modele.getJoueurHumain());
    }
    @Test
    public void testGetJoueurHumainAucun() {
        JoueurIA ia = new JoueurIA("Robot", 1000, new StrategieMock());
        modele.ajouterJoueur(ia);
        
        assertNull(modele.getJoueurHumain());
    }
    @Test
    public void testGetJoueurActif() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        joueur.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        
        assertEquals(joueur, modele.getJoueurActif());
    }
    
    @Test
    public void testGetJoueurActifIndex() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        joueur.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        
        assertEquals(0, modele.getJoueurActifIndex());
    }
   
    
/**
 * Tests sur les parties
 */    
    @Test
    public void testDemarrerPartie() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        joueur.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        
        assertEquals(EtatPartie.TOUR_JOUEURS, modele.getEtatPartie());
        assertEquals(2, joueur.getMainActuelle().getNombreCartes());
        assertEquals(2, modele.getCroupier().getMain().getNombreCartes());
    }
    @Test
    public void testResetPartie() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        joueur.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        modele.resetPartie();
        
        assertEquals(EtatPartie.EN_ATTENTE, modele.getEtatPartie());
        assertEquals(0, joueur.getNombreMains());
        assertEquals(0, modele.getCroupier().getMain().getNombreCartes());
    }
    
    

/**
 * Tests sur les tours de jeu
 */
    @Test
    public void testPasserAuJoueurSuivant() {
        Joueur joueur1 = new JoueurHumain("Alice", 1000);
        Joueur joueur2 = new JoueurHumain("Bob", 1000);
        
        modele.ajouterJoueur(joueur1);
        modele.ajouterJoueur(joueur2);
        
        joueur1.demarrerNouvelleMain(100);
        joueur2.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        assertEquals(0, modele.getJoueurActifIndex());
        modele.passerAuJoueurSuivant();
        
        assertEquals(1, modele.getJoueurActifIndex());
        assertEquals(joueur2, modele.getJoueurActif());
    }
    
    
/**
 * Tests sur le croupier 
*/    
    @Test
    public void testTourCroupier() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        joueur.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        modele.tourCroupier();
        
        assertEquals(EtatPartie.TERMINE, modele.getEtatPartie());
        assertTrue(modele.getCroupier().getRevelerDeuxiemeCarte());
    }
    @Test
    public void testGetCroupier() {
        Croupier croupier = modele.getCroupier();
        
        assertNotNull(croupier);
        assertSame(croupier, modele.getCroupier());
    }
    @Test
    public void testGetCarteVisibleCroupier() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        joueur.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        
        Carte carteVisible = modele.getCarteVisibleCroupier();
        
        assertNotNull(carteVisible);
        assertEquals(modele.getCroupier().getCarteVisible(), carteVisible);
    }
    
    @Test
    public void testGetCarteVisibleCroupierAvantPartie() {
        assertNull(modele.getCarteVisibleCroupier());
    }
    
    
    @Test
    public void testGetPioche() {
        Paquet pioche = modele.getPioche();
        
        assertNotNull(pioche);
        assertEquals(52, pioche.getPaquet().size());
    }
    
    
/**
 * Tests sur les mises
 */    
    @Test
    public void testSetMiseMinimale() {
        modele.setMiseMinimale(50);
        
        assertEquals(50, modele.getMiseMinimale());
    }
    @Test
    public void testSetMiseMinimaleNegative() {
        modele.setMiseMinimale(-10);
        
        assertEquals(10, modele.getMiseMinimale()); // Ne devrait pas changer
    }
    @Test
    public void testSetMiseMinimaleZero() {
        modele.setMiseMinimale(0);
        
        assertEquals(10, modele.getMiseMinimale()); // Ne devrait pas changer
    }
    
    
/**
 * Tests sur les joueurs
 */   
    @Test
    public void testGetJoueurs() {
        Joueur joueur1 = new JoueurHumain("Alice", 1000);
        Joueur joueur2 = new JoueurHumain("Bob", 1000);
        
        modele.ajouterJoueur(joueur1);
        modele.ajouterJoueur(joueur2);
        
        List<Joueur> joueurs = modele.getJoueurs();
        
        assertEquals(2, joueurs.size());
        assertTrue(joueurs.contains(joueur1));
        assertTrue(joueurs.contains(joueur2));
    }
    @Test
    public void testGetJoueursRetourneCopie() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        
        List<Joueur> joueurs1 = modele.getJoueurs();
        List<Joueur> joueurs2 = modele.getJoueurs();
        
        assertNotSame(joueurs1, joueurs2);
    }
    @Test
    public void testGetNombreJoueurs() {
        assertEquals(0, modele.getNombreJoueurs());
        
        modele.ajouterJoueur(new JoueurHumain("Alice", 1000));
        assertEquals(1, modele.getNombreJoueurs());
        
        modele.ajouterJoueur(new JoueurHumain("Bob", 1000));
        assertEquals(2, modele.getNombreJoueurs());
    }
    
    
/**
 * Tests sur les actions
 */    
    @Test
    public void testGetActionsDisponibles() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        joueur.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        
        List<Action> actions = modele.getActionsDisponibles(joueur);
        
        assertNotNull(actions);
        assertFalse(actions.isEmpty());
    }
    @Test
    public void testGetGestionnaireActions() {
        assertNotNull(modele.getGestionnaireActions());
    }   
    @Test
    public void testExecuterAction() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        joueur.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        
        // Tester l'exécution d'une action (si disponible)
        List<Action> actions = modele.getActionsDisponibles(joueur);
        if (!actions.isEmpty()) {
            Action action = actions.stream()
                .filter(a -> a.getNom().equals("TIRER"))
                .findFirst()
                .orElse(null);
            
            if (action != null) {
                modele.executerAction(action);
                assertTrue(joueur.getMainActuelle().getNombreCartes() >= nombreCartesAvant);
            }
        }
    }
    
    

/**
 * Tests sur les parties
 * Partie Normale
 * Partie avec IA
 * Partie avec des joueurs mixtes
 */    
    @Test
    public void testPartieComplete() {
        Joueur joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        joueur.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        assertEquals(EtatPartie.TOUR_JOUEURS, modele.getEtatPartie());
        
        modele.passerAuJoueurSuivant(); //Passer au croupier
        assertEquals(EtatPartie.TERMINE, modele.getEtatPartie());
        
        //Restart
        modele.resetPartie();
        assertEquals(EtatPartie.EN_ATTENTE, modele.getEtatPartie());
    }
    @Test
    public void testPartieAvecIA() {
        JoueurIA ia = new JoueurIA("Robot", 1000, new StrategieMock());
        modele.ajouterJoueur(ia);
        ia.demarrerNouvelleMain(100);
        
        modele.demarrerPartie();
        
        assertEquals(ia, modele.getJoueurActif());
        assertFalse(ia.estHumain());
    }
    @Test
    public void testPartieAvecPlusieursJoueursMixtes() {
        JoueurHumain humain = new JoueurHumain("Alice", 1000);
        JoueurIA ia1 = new JoueurIA("Robot1", 1000, new StrategieMock());
        JoueurIA ia2 = new JoueurIA("Robot2", 1000, new StrategieMock());
        
        modele.ajouterJoueur(humain);
        modele.ajouterJoueur(ia1);
        modele.ajouterJoueur(ia2);
        
        humain.demarrerNouvelleMain(100);
        ia1.demarrerNouvelleMain(50);
        ia2.demarrerNouvelleMain(75);
        
        modele.demarrerPartie();
        
        assertEquals(3, modele.getNombreJoueurs());
        assertEquals(EtatPartie.TOUR_JOUEURS, modele.getEtatPartie());
    }
}