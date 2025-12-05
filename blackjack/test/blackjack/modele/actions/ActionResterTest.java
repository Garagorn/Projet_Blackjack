package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import modele.cartes.Carte;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActionResterTest {
    
    private ModeleBlackjack modele;
    private ActionRester action;
    private Joueur joueur;
    
    @Before
    public void setUp() {
        modele = new ModeleBlackjack(10);
        action = new ActionRester(modele.getPioche());
        joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
    }
    
    @Test
    public void testGetNom() {
        assertEquals("Rester", action.getNom());
    }
    
    @Test
    public void testGetDescription() {
        assertNotNull(action.getDescription());
        assertTrue(action.getDescription().contains("main"));
    }
    
    @Test
    public void testPeutExecuterAvecJoueurNull() {
        assertFalse(action.peutExecuter(null));
    }
    
    @Test
    public void testPeutExecuterAvecMainNull() {
        Joueur joueurSansMain = new JoueurHumain("Bob", 1000);
        modele.ajouterJoueur(joueurSansMain);
        
        assertFalse(action.peutExecuter(joueurSansMain));
    }
    
    @Test
    public void testPeutExecuterAvecMainNormale() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("6", "Pique"));
        
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutExecuterAvecScore21() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("As", "Coeur"));
        joueur.ajouterCarte(new Carte("Roi", "Pique"));
        
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutExecuterAvecMainBustee() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("10", "Pique"));
        joueur.ajouterCarte(new Carte("5", "Carreau"));
        
        assertTrue(joueur.estBuste());
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutExecuterAvecScore17() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("7", "Pique"));
        
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutExecuterAvecScore20() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("10", "Pique"));
        
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutExecuterAvecScoreBas() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("3", "Coeur"));
        joueur.ajouterCarte(new Carte("2", "Pique"));
        
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testExecuter() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("7", "Pique"));
        
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        int scoreAvant = joueur.getScore();
        
        action.executer(joueur);
        
        assertEquals(nombreCartesAvant, joueur.getMainActuelle().getNombreCartes());
        assertEquals(scoreAvant, joueur.getScore());
    }
    
    @Test
    public void testExecuterNAjoutePasCarte() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("6", "Pique"));
        
        int taillePiocheAvant = modele.getPioche().getPaquet().size();
        
        action.executer(joueur);
        
        assertEquals(taillePiocheAvant, modele.getPioche().getPaquet().size());
    }
    
    @Test
    public void testExecuterSansPermission() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("10", "Pique"));
        joueur.ajouterCarte(new Carte("5", "Carreau"));
        
        assertTrue(joueur.estBuste());
        
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        
        action.executer(joueur);
        
        assertEquals(nombreCartesAvant, joueur.getMainActuelle().getNombreCartes());
    }
    
    @Test
    public void testExecuterAvecJoueurNull() {
        action.executer(null);
    }
    
    @Test
    public void testExecuterPlusieursfois() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("7", "Pique"));
        
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        
        action.executer(joueur);
        action.executer(joueur);
        action.executer(joueur);
        
        assertEquals(nombreCartesAvant, joueur.getMainActuelle().getNombreCartes());
    }
    
    @Test
    public void testPeutExecuterAvecBlackjack() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("As", "Coeur"));
        joueur.ajouterCarte(new Carte("Roi", "Pique"));
        
        assertTrue(joueur.aBlackjack());
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutToujoursResterSaufSiBuste() {
        for (int i = 4; i <= 21; i++) {
            Joueur testJoueur = new JoueurHumain("Test" + i, 1000);
            testJoueur.demarrerNouvelleMain(100);
            
            testJoueur.ajouterCarte(new Carte("10", "Coeur"));
            testJoueur.ajouterCarte(new Carte(String.valueOf(Math.min(i - 10, 10)), "Pique"));
            
            if (!testJoueur.estBuste()) {
                assertTrue("Devrait pouvoir rester avec score " + testJoueur.getScore(), 
                          action.peutExecuter(testJoueur));
            }
        }
    }
    
    @Test
    public void testExecuterNeChangeRien() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        int score = joueur.getScore();
        int cartes = joueur.getMainActuelle().getNombreCartes();
        int jetons = joueur.getJetons();
        int mise = joueur.getMiseActuelle();
        
        action.executer(joueur);
        
        assertEquals(score, joueur.getScore());
        assertEquals(cartes, joueur.getMainActuelle().getNombreCartes());
        assertEquals(jetons, joueur.getJetons());
        assertEquals(mise, joueur.getMiseActuelle());
    }
}
