package blackjack.modele.joueurs;

import modele.cartes.Carte;
import modele.cartes.Paquet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CroupierTest {
    
    /**
     * Mise  en place des variables
     */
    
    private Croupier croupier;
    private Paquet pioche;
    
    @Before
    public void setUp() {
        croupier = new Croupier();
        pioche = new Paquet();
    }
    
    
    /**
     * Le croupier possède :
     * Une main
     * Il n'a pas reveler  sa 2e carte
     * Son score est  null
     */
    @Test
    public void testConstructeur() {
        assertNotNull(croupier.getMain());
        assertFalse(croupier.getRevelerDeuxiemeCarte());
        assertEquals(0, croupier.getScore());
    }
    
    @Test
    public void testAjouterCarte() {
        Carte carte = new Carte("10","Coeur");
        croupier.ajouterCarte(carte);
        //0 à 10 de score
        assertEquals(10, croupier.getScore());
    }
    
    
/**
 * Visibilité des Scores et Cartes
 */
    @Test
    public void testGetCarteVisible() {
        Carte carte1 = new Carte("10","Coeur");
        Carte carte2 = new Carte("5","Pique");
        //Carte  1 visible mais carte 2
        croupier.ajouterCarte(carte1);
        croupier.ajouterCarte(carte2);
        
        assertEquals(carte1, croupier.getCarteVisible());
    }
    @Test
    public void testGetCarteVisibleMainVide() {
        assertNull(croupier.getCarteVisible());
    }
    @Test
    public void testGetScoreVisibleAvecAs() {
        //Visible et  prise en compte de la valeur d'un as
        croupier.ajouterCarte(new Carte("As","Coeur"));
        assertEquals(11, croupier.getScoreVisible());
    }
    @Test
    public void testGetScoreVisible() {
        //Visible et  prise en compte de la valeur d'une carte spécialeà
        croupier.ajouterCarte(new Carte("Roi","Coeur"));
        assertEquals(10, croupier.getScoreVisible());
    }
    @Test
    public void testGetScoreVisibleChiffre() {
        //Visible et  prise en compte de la valeur d'une carte lambda
        croupier.ajouterCarte(new Carte("7","Coeur"));
        assertEquals(7, croupier.getScoreVisible());
    }   
    
    
/**
 * Tests sur le Blackjack
 * As + 10/Valet/Dame/Roi
 */
    @Test
    public void testABlackjack() {
        croupier.ajouterCarte(new Carte("As","Coeur")); //Vaut  11 dans ce cas
        croupier.ajouterCarte(new Carte("Roi","Pique"));
        assertTrue(croupier.aBlackjack());
    }
    @Test
    public void testPasBlackjack() {
        croupier.ajouterCarte(new Carte("10","Coeur"));
        croupier.ajouterCarte(new Carte("9","Pique"));
        assertFalse(croupier.aBlackjack());
    }
    
    
 /**
  * Tests sur le Bust
  * A depasser 21
  */   
    @Test
    public void testEstBust() {
        croupier.ajouterCarte(new Carte("10","Coeur"));
        croupier.ajouterCarte(new Carte("10","Pique"));
        croupier.ajouterCarte(new Carte("5","Carreau"));
        assertTrue(croupier.estBuste());
    }
    @Test
    public void testPasBust() {
        croupier.ajouterCarte(new Carte("10","Coeur"));
        croupier.ajouterCarte(new Carte("9","Pique"));
        assertFalse(croupier.estBuste());
    }
    @Test
    public void testJouerBuster() {
        pioche.ajouter(new Carte("10","Coeur"));
        pioche.ajouter(new Carte("10","Pique"));
        pioche.ajouter(new Carte("10","Carreau"));
        
        croupier.jouer(pioche);
        assertTrue(croupier.estBuste() || croupier.getScore() >= 17);
    }
    
    
/**
 * Tests sur la condition d'arrêt du croupier à 17 de score
 */
    @Test
    public void testJouerTireTo17() {
        pioche.ajouter(new Carte("10","Coeur"));
        pioche.ajouter(new Carte("5","Pique"));
        pioche.ajouter(new Carte("3","Carreau"));
        
        croupier.jouer(pioche);
        //Depasse 17 et s'arrete
        assertTrue(croupier.getScore() >= 17);
    }   
    @Test
    public void testFinJouerSi17() {
        croupier.ajouterCarte(new Carte("10","Coeur"));
        croupier.ajouterCarte(new Carte("7","Pique"));
        pioche.ajouter(new Carte("5","Carreau"));
        
        int scoreAvant = croupier.getScore();
        croupier.jouer(pioche);
        //Ne pioche pas le 5 de carreau
        assertEquals(scoreAvant, croupier.getScore());
        assertEquals(1, pioche.getPaquet().size());
    }
    @Test
    public void testJouerPiocheVide() {
        croupier.ajouterCarte(new Carte("5","Coeur"));
        croupier.jouer(pioche);
        //Ne pioiche pas, plus de cartes
        assertEquals(5, croupier.getScore());
    }
    
    
/**
 * Tests sur les deuxiemes cartes
 */
    @Test
    public void testAfficherDeuxiemeCarte() {
        assertFalse(croupier.getRevelerDeuxiemeCarte());
        croupier.afficherDeuxiemeCarte();

        assertTrue(croupier.getRevelerDeuxiemeCarte());
    }
    @Test
    public void testCacherDeuxiemeCarte() {
        croupier.afficherDeuxiemeCarte();
        assertTrue(croupier.getRevelerDeuxiemeCarte());
        croupier.cacherDeuxiemeCarte();
        //Toujours cacher
        assertFalse(croupier.getRevelerDeuxiemeCarte());
    }
    
    
/**
 * Tests sur la visibilité du Pauqet
 */
    @Test
    public void testGetPaquetVisible() {
        croupier.ajouterCarte(new Carte("10","Coeur"));
        croupier.ajouterCarte(new Carte("5","Pique"));
        
        Paquet paquetVisible = croupier.getPaquetVisible();
        //Premiere carte quand le  paquet est visible
        assertEquals(1, paquetVisible.getPaquet().size());
        assertEquals("10", paquetVisible.getPaquet().get(0).hauteur);
    }
    
    
/**
 * Tests sur la main
 */
    @Test
    public void testGetPaquetVisibleMainVide() {
        Paquet paquetVisible = croupier.getPaquetVisible();
        assertTrue(paquetVisible.getPaquet().isEmpty());
    }
    @Test
    public void testReinitialiserMain() {
        croupier.ajouterCarte(new Carte("10","Coeur"));
        croupier.ajouterCarte(new Carte("9","Pique"));
        croupier.afficherDeuxiemeCarte();
        
        croupier.reinitialiserMain();
        //Suppression des cartes précédentes
        assertEquals(0, croupier.getScore());
        assertNull(croupier.getCarteVisible());
    }    
    @Test
    public void testGetScoreVisible0SiMainVide() {
        assertEquals(0, croupier.getScoreVisible());
    }
    
    
/**
 * Tests ToString
 */
    @Test
    public void testToString() {
        croupier.ajouterCarte(new Carte("As","Coeur"));
        croupier.ajouterCarte(new Carte("Roi","Pique"));
        
        String result = croupier.toString();
        
        assertTrue(result.contains("Croupier"));
        assertTrue(result.contains("Score: 21"));
        assertTrue(result.contains("BLACKJACK!"));
    }    
    @Test
    public void testToStringBuste() {
        croupier.ajouterCarte(new Carte("10","Coeur"));
        croupier.ajouterCarte(new Carte("10","Pique"));
        croupier.ajouterCarte(new Carte("5","Carreau"));
        
        String result = croupier.toString();
        
        assertTrue(result.contains("BRÛLÉ!"));
    }
    
    
/**
 * Adaptation du score avec un as
 */
    @Test
    public void testJouerAvecAs() {
        pioche.ajouter(new Carte("As","Coeur"));
        pioche.ajouter(new Carte("5","Pique"));
        pioche.ajouter(new Carte("2","Carreau"));
        
        croupier.jouer(pioche);
        
        assertTrue(croupier.getScore() >= 17);
    }
    
}
