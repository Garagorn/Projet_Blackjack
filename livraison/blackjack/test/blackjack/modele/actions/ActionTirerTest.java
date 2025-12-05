package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import modele.cartes.Carte;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActionTirerTest {
    
    private ModeleBlackjack modele;
    private ActionTirer action;
    private Joueur joueur;
    
    @Before
    public void setUp() {
        modele = new ModeleBlackjack(10);
        action = new ActionTirer(modele.getPioche());
        joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
    }
    
    @Test
    public void testGetNom() {
        assertEquals("Tirer", action.getNom());
    }
    
    @Test
    public void testGetDescription() {
        assertNotNull(action.getDescription());
        assertTrue(action.getDescription().contains("carte"));
    }
    
    
    @Test
    public void testPeutExecuterAvecJoueurNull() {
        assertFalse(action.peutExecuter(null));
    }
     

/**
 * Tests avec des mains tronquées
 */
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
    public void testPeutExecuterAvecMainNormale() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("6", "Pique"));
        
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
    public void testPeutExecuterAvecMainDoublee() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("5", "Coeur"));
        joueur.ajouterCarte(new Carte("5", "Pique"));
        
        joueur.getMainActuelle().doubler();
        
        assertFalse(action.peutExecuter(joueur));
    }
    
  
/**
 * Tests sur les executions
 */    
    @Test
    public void testPeutExecuterAvecPiocheVide() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("6", "Pique"));
        
        while (!modele.getPioche().estVide()) {
            Carte carte = modele.getPioche().getPaquet().get(0);
            modele.getPioche().retirer(carte);
        }
        
        assertFalse(action.peutExecuter(joueur));
    }
    @Test
    public void testExecuter() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("6", "Pique"));
        
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        int taillePiocheAvant = modele.getPioche().getPaquet().size();
        
        action.executer(joueur);
        
        assertEquals(nombreCartesAvant + 1, joueur.getMainActuelle().getNombreCartes());
        assertEquals(taillePiocheAvant - 1, modele.getPioche().getPaquet().size());
    }
    @Test
    public void testExecuterSansPermission() {
        joueur.demarrerNouvelleMain(100);
        
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("10", "Pique"));
        joueur.ajouterCarte(new Carte("5", "Carreau"));
        
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        
        action.executer(joueur);
        
        assertEquals(nombreCartesAvant, joueur.getMainActuelle().getNombreCartes());
    }
    @Test
    public void testExecuterPlusieursCartes() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("5", "Coeur"));
        joueur.ajouterCarte(new Carte("3", "Pique"));
        
        action.executer(joueur);
        assertEquals(3, joueur.getMainActuelle().getNombreCartes());
        
        action.executer(joueur);
        assertEquals(4, joueur.getMainActuelle().getNombreCartes());
        
        action.executer(joueur);
        assertEquals(5, joueur.getMainActuelle().getNombreCartes());
    }
    @Test
    public void testExecuterJusquaBuste() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("6", "Pique"));
        
        while (!joueur.estBuste() && action.peutExecuter(joueur)) {
            action.executer(joueur);
        }
        
        assertFalse(action.peutExecuter(joueur));
    }
    @Test
    public void testExecuterAvecJoueurNull() {
        int taillePiocheAvant = modele.getPioche().getPaquet().size();
        
        action.executer(null);
        
        assertEquals(taillePiocheAvant, modele.getPioche().getPaquet().size());
    }
    @Test
    public void testPeutExecuterAvecScore21() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("As", "Coeur"));
        joueur.ajouterCarte(new Carte("Roi", "Pique"));
        
        assertEquals(21, joueur.getScore());
        assertTrue(action.peutExecuter(joueur));
    }
    @Test
    public void testExecuterModifieScore() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("6", "Pique"));
        
        int scoreAvant = joueur.getScore();
        
        action.executer(joueur);
        
        assertNotEquals(scoreAvant, joueur.getScore());
    }
}
