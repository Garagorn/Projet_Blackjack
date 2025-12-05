package blackjack.modele.joueurs;

import modele.cartes.Carte;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class JoueurHumainTest {
    
    private JoueurHumain joueur;

    @Before
    public void setUp(){
        joueur = new JoueurHumain("Alice", 1000);
    }
    
    @Test
    public void testConstructeur(){
        assertEquals("Alice", joueur.getNom()); //Bon nom
        assertEquals(1000, joueur.getJetons()); //Bon nombre de jeton
        assertTrue(joueur.getMains().isEmpty()); //Main vid
        assertEquals(0, joueur.getGainsSession()); //Aucun gain
        assertEquals(0, joueur.getPertesSession()); //Aucune perte
    }
    
    @Test
    public void testEstHumain(){
        assertTrue(joueur.estHumain());
    }
      
    @Test
    public void testAjouterCarte(){
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10","Coeur"));
        assertEquals(10, joueur.getScore());
    }
    
    
/**
 * Test sur la main
 */
    @Test
    public void testDemarrerNouvelleMain(){
        joueur.demarrerNouvelleMain(100);
        assertEquals(1, joueur.getNombreMains());
        assertEquals(100, joueur.getMiseActuelle());
        assertEquals(900, joueur.getJetons());
        assertEquals(100, joueur.getPertesSession());
    }
    
    @Test
    public void testAjouterCarteSansMain(){
        joueur.ajouterCarte(new Carte("10","Coeur"));
        assertEquals(0, joueur.getScore());
    }
    
    @Test
    public void testAjouterMain(){
        Main nouvelleMain = new Main(50);
        joueur.ajouterMain(nouvelleMain);
        assertEquals(1, joueur.getNombreMains());
    }
    
    @Test
    public void testPasserAMainSuivante(){
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterMain(new Main(50));
        assertEquals(0, joueur.getMainActuelleIndex());
        joueur.passerAMainSuivante();
        assertEquals(1, joueur.getMainActuelleIndex());
    }
    
    @Test
    public void testPasserAMainSuivanteDerniereMain(){
        joueur.demarrerNouvelleMain(100);
        joueur.passerAMainSuivante();
        assertEquals(0, joueur.getMainActuelleIndex());
    }
    
    @Test
    public void testDeuxiemeMain(){
        joueur.demarrerNouvelleMain(100);
        assertFalse(joueur.aUneDeuxiemeMain());
        joueur.ajouterMain(new Main(50));
        assertTrue(joueur.aUneDeuxiemeMain());
        joueur.passerAMainSuivante();
        assertFalse(joueur.aUneDeuxiemeMain());
    }

    
/**
 * Tests  sur les jetons et les mises
 */
    @Test
    public void testDebiterJetons(){
        joueur.debiterJetons(200);
        assertEquals(800, joueur.getJetons());
        assertEquals(200, joueur.getPertesSession());
    }
    
    @Test
    public void testCrediterJetons(){
        joueur.crediterJetons(300);
        assertEquals(1300, joueur.getJetons());
        assertEquals(300, joueur.getGainsSession());
    }
    
    @Test
    public void testPeutMiser(){
        assertTrue(joueur.peutMiser(500));
        assertTrue(joueur.peutMiser(1000));
        assertFalse(joueur.peutMiser(1001));
    }
    
    @Test
    public void testGetScore(){
        assertEquals(0, joueur.getScore());
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10","Coeur"));
        joueur.ajouterCarte(new Carte("7","Pique"));
        assertEquals(17, joueur.getScore());
    }
    
    @Test
    public void testGetMiseActuelle(){
        assertEquals(0, joueur.getMiseActuelle());
        joueur.demarrerNouvelleMain(100);
        assertEquals(100, joueur.getMiseActuelle());
    }
    
    @Test
    public void testGetBilanSession(){
        joueur.debiterJetons(200);
        joueur.crediterJetons(500);
        assertEquals(300, joueur.getBilanSession());

        joueur.debiterJetons(600);
        assertEquals(-300, joueur.getBilanSession());
    }
    
    @Test
    public void testABlackjack(){
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("As","Coeur"));
        joueur.ajouterCarte(new Carte("10","Pique"));
        assertTrue(joueur.aBlackjack());
    }
    
    @Test
    public void testPasBlackjack(){
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10","Coeur"));
        joueur.ajouterCarte(new Carte("9","Pique"));
        assertFalse(joueur.aBlackjack());
    }
    
    @Test
    public void testEstBuste(){
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10","Coeur"));
        joueur.ajouterCarte(new Carte("10","Pique"));
        joueur.ajouterCarte(new Carte("5","Carreau"));
        assertTrue(joueur.estBuste());
    }
    
    @Test
    public void testReinitialiserMains(){
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterMain(new Main(50));
        joueur.reinitialiserMains();
        assertEquals(0, joueur.getNombreMains());
        assertEquals(0, joueur.getMainActuelleIndex());
    }
    
    @Test
    public void testReinitialiserSession(){
        joueur.debiterJetons(200);
        joueur.crediterJetons(500);
        joueur.reinitialiserSession();
        assertEquals(0, joueur.getGainsSession());
        assertEquals(0, joueur.getPertesSession());
        assertEquals(0, joueur.getBilanSession());
    }


/**
 * Tests get/Set
 */    
    @Test
    public void testSetNom(){
        joueur.setNom("Bob");
        assertEquals("Bob", joueur.getNom());
    }
    @Test
    public void testSetJetons(){
        joueur.setJetons(2000);
        assertEquals(2000, joueur.getJetons());
    }
    @Test
    public void testGetMainActuelleVide(){
        assertNull(joueur.getMainActuelle());
    }


/**
 * Tests de Equals et HashCode
 */    
    @Test
    public void testEquals(){
        JoueurHumain autre = new JoueurHumain("Alice", 500);
        JoueurHumain diff = new JoueurHumain("Bob", 1000);
        
        assertEquals(joueur, autre);
        assertNotEquals(joueur, diff);
        assertEquals(joueur, joueur);
        assertFalse(joueur == null);
        assertFalse(joueur.equals("Alice"));
    }
    @Test
    public void testHashCode(){
        JoueurHumain autre = new JoueurHumain("Alice", 500);
        assertEquals(joueur.hashCode(), autre.hashCode());
    }
    
    
/**
 * Tests la perte de multiples crédits
 */   
    @Test
    public void testMultiplesDebitsCredits(){
        joueur.debiterJetons(100);
        joueur.debiterJetons(50);
        joueur.crediterJetons(200);
        joueur.crediterJetons(100);
        assertEquals(1150, joueur.getJetons());
        assertEquals(150, joueur.getPertesSession());
        assertEquals(300, joueur.getGainsSession());
    }
    
    
/**
 * Tests sur les mains
 */
    @Test
    public void testGetMains(){
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterMain(new Main(50));
        assertEquals(2, joueur.getMains().size());
    }
    @Test
    public void testDemarrerPlusieursMainsConsecutives(){
        joueur.demarrerNouvelleMain(100);
        joueur.demarrerNouvelleMain(50);
        joueur.demarrerNouvelleMain(75);
        assertEquals(3, joueur.getNombreMains());
        assertEquals(775, joueur.getJetons());
        assertEquals(225, joueur.getPertesSession());
    }
}