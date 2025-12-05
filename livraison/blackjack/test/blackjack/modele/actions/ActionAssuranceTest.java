package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import modele.cartes.Carte;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActionAssuranceTest {
    
    private ModeleBlackjack modele;
    private ActionAssurance action;
    private Joueur joueur;
    private Croupier croupier;
    
    @Before
    public void setUp() {
        modele = new ModeleBlackjack(10);
        action = new ActionAssurance(modele.getPioche(), modele.getCroupier());
        joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
        croupier = modele.getCroupier();
    }
    
    @Test
    public void testGetNom() {
        assertEquals("Assurance", action.getNom());
    }
    
    @Test
    public void testGetDescription() {
        assertNotNull(action.getDescription());
        assertTrue(action.getDescription().contains("Blackjack"));
    }
    
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
    public void testPeutExecuterAvecCroupierMontrantAs() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("As", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testNePeutPasExecuterSansCroupierMontrantAs() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("10", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testNePeutPasExecuterAvecCroupierSans10() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("7", "Carreau"));
        
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testNePeutPasExecuterSansJetons() {
        joueur.setJetons(100);
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("As", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        assertEquals(0, joueur.getJetons());
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testPeutExecuterAvecJustesAssezJetons() {
        joueur.setJetons(150);
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("As", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        assertEquals(50, joueur.getJetons());
        assertTrue(action.peutExecuter(joueur));
    }
    
    @Test
    public void testNePeutPasExecuterAvecMainDejaAssuree() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        joueur.getMainActuelle().setAssure(true);
        
        croupier.ajouterCarte(new Carte("As", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testNePeutPasExecuterAvecTroisCartes() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("5", "Coeur"));
        joueur.ajouterCarte(new Carte("3", "Pique"));
        joueur.ajouterCarte(new Carte("2", "Carreau"));
        
        croupier.ajouterCarte(new Carte("As", "Trefle"));
        croupier.ajouterCarte(new Carte("7", "Coeur"));
        
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testExecuter() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("As", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        int jetonsAvant = joueur.getJetons();
        
        action.executer(joueur);
        
        assertEquals(jetonsAvant - 50, joueur.getJetons());
        assertTrue(joueur.getMainActuelle().estAssure());
    }
    
    @Test
    public void testExecuterCoutAssuranceMoitieMise() {
        joueur.demarrerNouvelleMain(200);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("As", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        int jetonsAvant = joueur.getJetons();
        
        action.executer(joueur);
        
        assertEquals(jetonsAvant - 100, joueur.getJetons());
    }
    
    @Test
    public void testExecuterSansPermission() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("10", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        int jetonsAvant = joueur.getJetons();
        
        action.executer(joueur);
        
        assertEquals(jetonsAvant, joueur.getJetons());
        assertFalse(joueur.getMainActuelle().estAssure());
    }
    
    @Test
    public void testExecuterAvecJoueurNull() {
        action.executer(null);
    }
    
    @Test
    public void testNePeutPasPrendreAssuranceDeuxFois() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("As", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        action.executer(joueur);
        
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testAssuranceAvecMise50() {
        joueur.demarrerNouvelleMain(50);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("As", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        action.executer(joueur);
        
        assertEquals(925, joueur.getJetons());
    }
    
    @Test
    public void testAssuranceAvecMiseImpaire() {
        joueur.demarrerNouvelleMain(101);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("9", "Pique"));
        
        croupier.ajouterCarte(new Carte("As", "Carreau"));
        croupier.ajouterCarte(new Carte("7", "Trefle"));
        
        action.executer(joueur);
        
        assertEquals(849, joueur.getJetons()); // 1000 - 101 (mise) - 50 (assurance)
    }
    
    @Test
    public void testPeutExecuterAvecCarteVisibleNull() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10","Coeur"));
        joueur.ajouterCarte(new Carte("9","Pique"));
        
        // Croupier sans carte
        assertFalse(action.peutExecuter(joueur));
    }
    
    @Test
    public void testAssuranceMarqueMain() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10","Coeur"));
        joueur.ajouterCarte(new Carte("9","Pique"));
        
        croupier.ajouterCarte(new Carte("As","Carreau"));
        croupier.ajouterCarte(new Carte("7","Trefle"));
        
        assertFalse(joueur.getMainActuelle().estAssure());
        
        action.executer(joueur);
        
        assertTrue(joueur.getMainActuelle().estAssure());
    }
    
    @Test
    public void testPeutExecuterNecessiteDeuxCartes() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10","Coeur"));
        
        croupier.ajouterCarte(new Carte("As","Carreau"));
        croupier.ajouterCarte(new Carte("7","Trefle"));
        
        // Seulement une carte
        assertFalse(action.peutExecuter(joueur));
    }
}