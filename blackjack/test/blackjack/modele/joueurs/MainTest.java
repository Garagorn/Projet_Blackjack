package blackjack.modele.joueurs;

import modele.cartes.Carte;
import modele.cartes.Paquet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    
    private Main main;
    
    /**
     * Pour avoir acces à main partout sans avoir à la déclarer à  chaque fois
     */  
    @Before
    public void setUp() {
        main = new Main();
    }
    
    @Test
    public void testConstructeur() {
        assertEquals(0, main.getMise());
        assertEquals(0, main.getScore());
        assertFalse(main.estSplit());
        assertFalse(main.estAssure());
        assertFalse(main.estDoublee());
        assertTrue(main.estVide());
        Main mainAvecMise = new Main(100);
        assertEquals(100, mainAvecMise.getMise());
    }
    
    /**
     * Test pour l'ajout d'une carte
     */
    @Test
    public void testAjouterCarte() {
        Carte carte = new Carte("5","Coeur");
        main.ajouterCarte(carte);
        
        assertEquals(1, main.getNombreCartes()); //Une seule carte ajoutée
        assertEquals(5, main.getScore());
        assertFalse(main.estVide());
    } 

/**
 * Tests sur le calcul de scores
 */
    @Test
    public void testCalculerScoreCartes() {
        main.ajouterCarte(new Carte("Valet","Coeur"));
        assertEquals(10, main.getScore());
        
        main.ajouterCarte(new Carte("Dame","Pique"));
        assertEquals(20, main.getScore());
        
        main.ajouterCarte(new Carte("Roi","Carreau"));
        assertEquals(30, main.getScore());
    }
    @Test
    public void testCalculerScoreAs() {
        main.ajouterCarte(new Carte("As","Coeur"));
        assertEquals(11, main.getScore());
        //Valeur non  changée
        main.ajouterCarte(new Carte("9","Pique"));
        assertEquals(20, main.getScore());
    }
    @Test
    public void testCalculerScoreAsAjuste() {
        main.ajouterCarte(new Carte("As","Coeur"));
        main.ajouterCarte(new Carte("Roi","Pique"));
        assertEquals(21, main.getScore());
        
        //Adapte la valeur de l'as
        main.ajouterCarte(new Carte("5","Carreau"));
        assertEquals(16, main.getScore());
    }
    @Test
    public void testCalculerScorePlusieursAs() {
        main.ajouterCarte(new Carte("As","Coeur"));
        main.ajouterCarte(new Carte("As","Pique"));
        main.ajouterCarte(new Carte("9","Carreau"));
        assertEquals(21, main.getScore());
    }
    
    
/**
 * Tests Blackjack
 * Blackjack seulement possible avec as + carte a 10 de hauteur
 */
    @Test
    public void testEstBlackjackAsDix() {
        main.ajouterCarte(new Carte("As","Coeur"));
        main.ajouterCarte(new Carte("10","Pique"));
        assertTrue(main.estBlackjack());
    } 
    @Test
    public void testEstBlackjackAs() {
        main.ajouterCarte(new Carte("As","Coeur"));
        main.ajouterCarte(new Carte("Roi","Pique"));
        assertTrue(main.estBlackjack());
    }
    @Test
    public void testPasBlackjTroisCartes() {
        main.ajouterCarte(new Carte("5","Coeur"));
        main.ajouterCarte(new Carte("6","Pique"));
        main.ajouterCarte(new Carte("10","Carreau"));
        assertFalse(main.estBlackjack());
    }
    @Test
    public void testPasBlackjackSans21() {
        main.ajouterCarte(new Carte("10","Coeur"));
        main.ajouterCarte(new Carte("9","Pique"));
        assertFalse(main.estBlackjack());
    }

    
/**
 * Tests Bust
 */
    @Test
    public void testEstBust() {
        main.ajouterCarte(new Carte("10","Coeur"));
        main.ajouterCarte(new Carte("10","Pique"));
        main.ajouterCarte(new Carte("5","Carreau"));
        assertTrue(main.estBuste());
        assertEquals(25, main.getScore());
    }
    @Test
    public void testPasEstBust() {
        main.ajouterCarte(new Carte("10","Coeur"));
        main.ajouterCarte(new Carte("10","Pique"));
        assertFalse(main.estBuste());
    }
   
/**
 * Tests sur le doublement de score
 * Limité si entre 9 et 1
 * Limité à la possession de 2 cartes
 */    
    @Test
    public void testPeutDoublerScore9() {
        main.ajouterCarte(new Carte("5","Coeur"));
        main.ajouterCarte(new Carte("4","Pique"));
        assertTrue(main.peutDoubler());
    }
    @Test
    public void testPeutDoublerScore10() {
        main.ajouterCarte(new Carte("5","Coeur"));
        main.ajouterCarte(new Carte("5","Pique"));
        assertTrue(main.peutDoubler());
    } 
    @Test
    public void testPeutDoublerScore11() {
        main.ajouterCarte(new Carte("6","Coeur"));
        main.ajouterCarte(new Carte("5","Pique"));
        assertTrue(main.peutDoubler());
    }
    @Test
    public void testNePeutPasDoublerScore8() {
        main.ajouterCarte(new Carte("5","Coeur"));
        main.ajouterCarte(new Carte("3","Pique"));
        assertFalse(main.peutDoubler());
    }
    @Test
    public void testNePeutPasDoublerScore12() {
        main.ajouterCarte(new Carte("7","Coeur"));
        main.ajouterCarte(new Carte("5","Pique"));
        assertFalse(main.peutDoubler());
    }
    @Test
    public void testNePeutPasDoublerAvecTroisCartes() {
        main.ajouterCarte(new Carte("3","Coeur"));
        main.ajouterCarte(new Carte("3","Pique"));
        main.ajouterCarte(new Carte("5","Carreau"));
        assertFalse(main.peutDoubler());
    }
    //Tentative dedoubler une deuxieme fois
    @Test
    public void testNePeutPasDoublerDeuxFois() {
        main.ajouterCarte(new Carte("5","Coeur"));
        main.ajouterCarte(new Carte("5","Pique"));
        main.doubler();
        assertFalse(main.peutDoubler());
    }
    @Test
    public void testDoubler() {
        Main mainAvecMise = new Main(100);
        mainAvecMise.ajouterCarte(new Carte("5","Coeur"));
        mainAvecMise.ajouterCarte(new Carte("5","Pique"));
        
        mainAvecMise.doubler();
        
        assertEquals(200, mainAvecMise.getMise());
        assertTrue(mainAvecMise.estDoublee());
    }
    

/**
 * Test pour split
 */    
    @Test
    public void testPeutSplitCartesIdentiques() {
        main.ajouterCarte(new Carte("8","Coeur"));
        main.ajouterCarte(new Carte("8","Pique"));
        assertTrue(main.peutSplit());
    }
    @Test
    public void testPeutSplitAs() {
        main.ajouterCarte(new Carte("As","Coeur"));
        main.ajouterCarte(new Carte("As","Pique"));
        assertTrue(main.peutSplit());
    }
    @Test
    public void testPeutSplitFigures() {
        main.ajouterCarte(new Carte("Roi","Coeur"));
        main.ajouterCarte(new Carte("Dame","Pique"));
        assertTrue(main.peutSplit());
    }
    @Test
    public void testNePeutPasSplitCartesDifferentes() {
        main.ajouterCarte(new Carte("8","Coeur"));
        main.ajouterCarte(new Carte("9","Pique"));
        assertFalse(main.peutSplit());
    }
    @Test
    public void testNePeutPasSplitAvecTroisCartes() {
        main.ajouterCarte(new Carte("8","Coeur"));
        main.ajouterCarte(new Carte("8","Pique"));
        main.ajouterCarte(new Carte("5","Carreau"));
        assertFalse(main.peutSplit());
    }
    @Test
    public void testSplit() {
        Main mainAvecMise = new Main(100);
        mainAvecMise.ajouterCarte(new Carte("8","Coeur"));
        mainAvecMise.ajouterCarte(new Carte("8","Pique"));
        
        Main nouvelleMain = mainAvecMise.split();
        
        assertEquals(1, mainAvecMise.getNombreCartes());
        assertEquals(1, nouvelleMain.getNombreCartes());
        assertTrue(mainAvecMise.estSplit());
        assertTrue(nouvelleMain.estSplit());
        assertEquals(100, nouvelleMain.getMise());
    }
    
/**
 * Tests pour retrer des cartes
 */
    @Test
    public void testRetirerCarte() {
        Carte carte = new Carte("5","Coeur");
        main.ajouterCarte(carte);
        main.ajouterCarte(new Carte("3","Pique"));
        
        main.retirerCarte(carte);
        
        assertEquals(1, main.getNombreCartes());
        assertEquals(3, main.getScore());
    }
    
/**
 * Tests de reinitialisation
 */    
    @Test
    public void testReinitialiser() {
        Main mainAvecMise = new Main(100);
        mainAvecMise.ajouterCarte(new Carte("10","Coeur"));
        mainAvecMise.ajouterCarte(new Carte("9","Pique"));
        //mainAvecMise.doubler(); Impossible de doubler -> ExeptionError
        mainAvecMise.setAssure(true);
        mainAvecMise.reinitialiser();
        
        assertEquals(0, mainAvecMise.getMise());
        assertEquals(0, mainAvecMise.getScore());
        assertEquals(0, mainAvecMise.getNombreCartes());

        assertFalse(mainAvecMise.estDoublee());
        assertFalse(mainAvecMise.estAssure());
        assertFalse(mainAvecMise.estSplit());
    }
 
/**
 * Test de ToString
 */    
    @Test
    public void testToString() {
        Main mainAvecMise = new Main(100);
        mainAvecMise.ajouterCarte(new Carte("As","Coeur"));
        mainAvecMise.ajouterCarte(new Carte("Roi","Pique"));
        
        String result = mainAvecMise.toString();
        
        assertTrue(result.contains("Score: 21"));
        assertTrue(result.contains("Cartes: 2"));
        assertTrue(result.contains("Mise: 100"));
        assertTrue(result.contains("BLACKJACK!"));
    }
    
    //Test  ToStringBust
    //Test  ToStringBlackjack
    //...
}