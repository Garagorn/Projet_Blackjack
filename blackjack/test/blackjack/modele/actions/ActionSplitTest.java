package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import modele.cartes.Carte;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActionSplitTest {
    
    private ModeleBlackjack modele;
    private ActionSplit action;
    private Joueur joueur;
    
    @Before
    public void setUp() {
        modele = new ModeleBlackjack(10);
        action = new ActionSplit(modele.getPioche());
        joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
    }
    
    @Test
    public void testGetNom() {
        assertEquals("Séparer", action.getNom());
    }
    
    @Test
    public void testGetDescription() {
        assertNotNull(action.getDescription());
        assertTrue(action.getDescription().contains("main"));
    }

    
/**
 * Tests executions tronquées
 */
    @Test
    public void testPeutExecuterAvecJoueurNull() {
        assertFalse(action.peutExecuter(null));
    }
    @Test
    public void testPeutExecuterAvecMainVide() {
        joueur.demarrerNouvelleMain(100);
        
        while (!joueur.getMainActuelle().estVide()) {
            Carte carte = joueur.getMainActuelle().getPaquetMain().getPaquet().get(0);
            joueur.getMainActuelle().retirerCarte(carte);
        }
        
        assertFalse(action.peutExecuter(joueur));
    }
    @Test
    public void testPeutExecuterAvecPaireAs() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("As", "Coeur"));
        joueur.ajouterCarte(new Carte("As", "Pique"));
        
        assertTrue(action.peutExecuter(joueur));
    }
    @Test
    public void testPeutExecuterAvecPaire8() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("8", "Pique"));
        
        assertTrue(action.peutExecuter(joueur));
    }
    @Test
    public void testPeutExecuterAvecPaireFigures() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("Roi", "Coeur"));
        joueur.ajouterCarte(new Carte("Dame", "Pique"));
        
        // Deux figures = valeur 10 = peut split
        assertTrue(action.peutExecuter(joueur));
    }
    @Test
    public void testNePeutPasSpliterCartesDifferentes() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        assertFalse(action.peutExecuter(joueur));
    }
    @Test
    public void testNePeutPasSpliterAvecTroisCartes() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("8", "Pique"));
        joueur.ajouterCarte(new Carte("5", "Carreau"));
        
        assertFalse(action.peutExecuter(joueur));
    }
    @Test
    public void testNePeutPasSpliterSansJetons() {
        joueur.setJetons(100);
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("8", "Pique"));
        
        assertEquals(0, joueur.getJetons());
        assertFalse(action.peutExecuter(joueur));
    }
    @Test
    public void testPeutSpliterAssezJetons() {
        joueur.setJetons(200);
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("8", "Pique"));
        
        assertEquals(100, joueur.getJetons());
        assertTrue(action.peutExecuter(joueur));
    }
    @Test
    public void testNePeutPasSplitPiocheVide() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("8", "Pique"));
        
        while (!modele.getPioche().estVide()) {
            Carte carte = modele.getPioche().getPaquet().get(0);
            modele.getPioche().retirer(carte);
        }
        
        assertFalse(action.peutExecuter(joueur));
    }
    @Test
    public void testExecuter() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("8", "Pique"));
        
        int jetonsAvant = joueur.getJetons();
        int nombreMainsAvant = joueur.getNombreMains();
        
        action.executer(joueur);
        
        assertEquals(nombreMainsAvant + 1, joueur.getNombreMains());
        assertEquals(jetonsAvant - 100, joueur.getJetons());
        assertEquals(2, joueur.getMains().get(0).getNombreCartes());
        assertEquals(2, joueur.getMains().get(1).getNombreCartes());
        assertEquals(100, joueur.getMains().get(0).getMise());
        assertEquals(100, joueur.getMains().get(1).getMise());
    }
    @Test
    public void testExecuterDistribueNouvelllesCartes() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("8", "Pique"));
        
        int taillePiocheAvant = modele.getPioche().getPaquet().size();
        
        action.executer(joueur);
        
        assertEquals(taillePiocheAvant - 2, modele.getPioche().getPaquet().size());
    }
    @Test
    public void testExecuterSansPermission() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        int jetonsAvant = joueur.getJetons();
        int nombreMainsAvant = joueur.getNombreMains();
        
        action.executer(joueur);
        
        assertEquals(jetonsAvant, joueur.getJetons());
        assertEquals(nombreMainsAvant, joueur.getNombreMains());
    }
    @Test
    public void testExecuterAvecJoueurNull() {
        action.executer(null);
    }
    @Test
    public void testMainsMarqueesSplit() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("8", "Pique"));
        
        action.executer(joueur);
        
        assertTrue(joueur.getMains().get(0).estSplit());
        assertTrue(joueur.getMains().get(1).estSplit());
    }
    @Test
    public void testSplitAvecAs() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("As", "Coeur"));
        joueur.ajouterCarte(new Carte("As", "Pique"));
        
        action.executer(joueur);
        
        assertEquals(2, joueur.getNombreMains());
        assertEquals(2, joueur.getMains().get(0).getNombreCartes());
        assertEquals(2, joueur.getMains().get(1).getNombreCartes());
    }
    @Test
    public void testSplitAvecMise50() {
        joueur.demarrerNouvelleMain(50);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("8", "Pique"));
        
        action.executer(joueur);
        
        assertEquals(900, joueur.getJetons());
        assertEquals(50, joueur.getMains().get(0).getMise());
        assertEquals(50, joueur.getMains().get(1).getMise());
    }
    @Test
    public void testNePeutPlusSpliterApresUnSplit() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterCarte(new Carte("8", "Pique"));
        
        action.executer(joueur);
        
        assertFalse(joueur.getMains().get(0).peutSplit());
    }
    @Test
    public void testSplitAvecPaireDe10() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("10", "Pique"));
        
        action.executer(joueur);
        
        assertEquals(2, joueur.getNombreMains());
    }
    @Test
    public void testJoueurAMainActuelleSurPremiereMainApresSplit() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8","Coeur"));
        joueur.ajouterCarte(new Carte("8","Pique"));
        
        action.executer(joueur);
        
        // Le joueur devrait être sur la première main
        assertEquals(0, joueur.getMainActuelleIndex());
    }
}