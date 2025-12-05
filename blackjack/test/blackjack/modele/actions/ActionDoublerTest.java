package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import modele.cartes.Carte;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActionDoublerTest {
    
    private ModeleBlackjack modele;
    private ActionDoubler action;
    private Joueur joueur;
    
    @Before
    public void setUp() {
        modele = new ModeleBlackjack(10);
        action = new ActionDoubler(modele.getPioche());
        joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
    }
    
    @Test
    public void testGetNom() {
        assertEquals("Doubler", action.getNom());
    }
    
    @Test
    public void testGetDescription() {
        assertNotNull(action.getDescription());
        assertTrue(action.getDescription().contains("mise"));
    }
    
    @Test
    public void testPeutExecuterAvecJoueurNull() {
        assertFalse(action.peutExecuter(null));
    }
    
    @Test
    public void testPeutExecuterAvecMainVide() {
        joueur.demarrerNouvelleMain(100);
        
        // Retirer toutes les cartes
        while (!joueur.getMainActuelle().estVide()) {
            Carte carte = joueur.getMainActuelle().getPaquetMain().getPaquet().get(0);
            joueur.getMainActuelle().retirerCarte(carte);
        }
        
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutExecuterAvecScore9() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("5", "Coeur"));
        joueur.ajouterCarte(new Carte("4", "Pique"));
        
        assertEquals(9, joueur.getScore());
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutExecuterAvecScore10() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("4", "Pique"));
        
        assertEquals(10, joueur.getScore());
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutExecuterAvecScore11() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        assertEquals(11, joueur.getScore());
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testNePeutPasDoublerAvecScore8() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("5", "Coeur"));
        joueur.ajouterCarte(new Carte("3", "Pique"));
        
        assertEquals(8, joueur.getScore());
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testNePeutPasDoublerAvecScore12() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("7", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        assertEquals(12, joueur.getScore());
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testNePeutPasDoublerAvecTroisCartes() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("3", "Coeur"));
        joueur.ajouterCarte(new Carte("3", "Pique"));
        joueur.ajouterCarte(new Carte("5", "Carreau"));
        
        assertEquals(11, joueur.getScore());
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testNePeutPasDoublerSansJetons() {
        joueur.setJetons(50);
        joueur.demarrerNouvelleMain(50);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        assertEquals(0, joueur.getJetons());
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutDoublerAvecJustesAssezJetons() {
        joueur.setJetons(200);
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        assertEquals(100, joueur.getJetons());
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testNePeutPasDoublerAvecPiocheVide() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        while (!modele.getPioche().estVide()) {
            Carte carte = modele.getPioche().getPaquet().get(0);
            modele.getPioche().retirer(carte);
        }
        
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testExecuter() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        int jetonsAvant = joueur.getJetons();
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        int miseAvant = joueur.getMiseActuelle();
        
        action.executer(joueur);
        
        assertEquals(miseAvant * 2, joueur.getMiseActuelle());
        assertEquals(jetonsAvant - 100, joueur.getJetons());
        assertEquals(nombreCartesAvant + 1, joueur.getMainActuelle().getNombreCartes());
        assertTrue(joueur.getMainActuelle().estDoublee());
    }
    
    @Test
    public void testExecuterAjouteUneSeuleCarte() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        action.executer(joueur);
        
        assertEquals(3, joueur.getMainActuelle().getNombreCartes());
    }
    
    @Test
    public void testExecuterSansPermission() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("10", "Pique"));
        
        int jetonsAvant = joueur.getJetons();
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        
        action.executer(joueur);
        
        assertEquals(jetonsAvant, joueur.getJetons());
        assertEquals(nombreCartesAvant, joueur.getMainActuelle().getNombreCartes());
    }
    
    @Test
    public void testNePeutPasDoublerDeuxFois() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        action.executer(joueur);
        
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testExecuterAvecJoueurNull() {
        action.executer(null);
    }
    
    @Test
    public void testExecuterDiminuePioche() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        int taillePiocheAvant = modele.getPioche().getPaquet().size();
        
        action.executer(joueur);
        
        assertEquals(taillePiocheAvant - 1, modele.getPioche().getPaquet().size());
    }
    
    @Test
    public void testExecuterPeutCauserBuste() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        action.executer(joueur);
        
        assertTrue(joueur.getScore() > 11);
    }
    
    @Test
    public void testDoublerAvecMise50() {
        joueur.demarrerNouvelleMain(50);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        action.executer(joueur);
        
        assertEquals(100, joueur.getMiseActuelle());
        assertEquals(900, joueur.getJetons());
    }
    
    @Test
    public void testDoublerAvecMise200() {
        joueur.demarrerNouvelleMain(200);
        joueur.ajouterCarte(new Carte("6", "Coeur"));
        joueur.ajouterCarte(new Carte("5","Pique"));
        
        action.executer(joueur);
        
        assertEquals(400, joueur.getMiseActuelle());
        assertEquals(600, joueur.getJetons()); // 1000 - 200 (mise initiale) - 200 (doublement)
    }
}