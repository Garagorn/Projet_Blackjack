package modele.cartes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tellier212@CAMPUS
 */
public class JoueurIATest {
    
    private JoueurIA joueur;
    private Carte carteAs;
    private Carte carteRoi;
    private Carte carte10;
    
    @Before
    public void setUp(){
        joueur = new JoueurIA("IA");
        carteAs = new Carte("As", "Pique");
        carteRoi = new Carte("Roi", "Coeur");
        carte10 = new Carte("10", "Carreau");
    }
    
    
    @Test
    public void testConstructeur(){
        JoueurIA ia = new JoueurIA("IA1");
        assertEquals("IA1", ia.getNom());
        assertNotNull(ia.getMain());
        assertTrue(ia.getMain().estVide());
    }
    
    @Test
    public void testConstructeurAvecNomVide(){
        JoueurIA ia = new JoueurIA("");
        assertEquals("", ia.getNom());
        assertNotNull(ia.getMain());
    }
    
    /**
     * Tests get des noms
     */
    
    @Test
    public void testGetNom(){
        assertEquals("IA", joueur.getNom());
    }
    
    @Test
    public void testGetNomDifferents(){
        JoueurIA ia1 = new JoueurIA("IA1");
        JoueurIA ia2 = new JoueurIA("IA2");
        
        assertEquals("IA1", ia1.getNom());
        assertEquals("IA2", ia2.getNom());
        assertNotEquals(ia1.getNom(), ia2.getNom());
    }
    
    
    /**
     * Tests de get des mains
     */
    
    @Test
    public void testGetMain(){
        assertNotNull(joueur.getMain());
        assertTrue(joueur.getMain().estVide());
    }
    
    @Test
    public void testGetMainNonNull(){
        JoueurIA ia = new JoueurIA("Test");
        assertNotNull(ia.getMain());
    }
    
    /**
     * Tests pour recevoir des cartes
     */
    
    @Test
    public void testRecevoirCarte(){
        joueur.recevoirCarte(carteAs);
        assertTrue(joueur.getMain().contient(carteAs));
        assertEquals(1, joueur.getMain().nbr_carte());
        assertFalse(joueur.getMain().estVide());
    }
    
    @Test
    public void testRecevoirDeuxCartes(){
        joueur.recevoirCarte(carteAs);
        joueur.recevoirCarte(carteRoi);
        
        assertEquals(2, joueur.getMain().nbr_carte());
        assertTrue(joueur.getMain().contient(carteAs));
        assertTrue(joueur.getMain().contient(carteRoi));
    }
    
    @Test
    public void testRecevoirTroisCartes(){
        joueur.recevoirCarte(carteAs);
        joueur.recevoirCarte(carteRoi);
        joueur.recevoirCarte(carte10);
        
        assertEquals(3, joueur.getMain().nbr_carte());
        assertTrue(joueur.getMain().contient(carteAs));
        assertTrue(joueur.getMain().contient(carteRoi));
        assertTrue(joueur.getMain().contient(carte10));
    }
    
    @Test
    public void testRecevoirMemeCarte(){
        joueur.recevoirCarte(carteAs);
        joueur.recevoirCarte(carteAs);
        
        assertEquals(2, joueur.getMain().nbr_carte());
    }
    
    
    /**
     * Tests pour jouer un ou des cartes
     */
    
    @Test
    public void testJouerCarte(){
        joueur.recevoirCarte(carteAs);
        joueur.recevoirCarte(carteRoi);
        joueur.recevoirCarte(carte10);
        
        Carte jouee = joueur.jouerCarte();
        
        assertNotNull(jouee);
        assertEquals(2, joueur.getMain().nbr_carte());
        assertFalse(joueur.getMain().contient(jouee));
    }
    
    @Test
    public void testJouerCarteAvecUneSeule(){
        joueur.recevoirCarte(carteAs);
        Carte jouee = joueur.jouerCarte();
        
        assertEquals(carteAs, jouee);
        assertTrue(joueur.getMain().estVide());
    }
    
    @Test
    public void testJouerCarteRetireDeMain(){
        joueur.recevoirCarte(carteAs);
        int tailleAvant = joueur.getMain().nbr_carte();
        joueur.jouerCarte();
        
        assertEquals(tailleAvant - 1, joueur.getMain().nbr_carte());
    }
    
    @Test
    public void testJouerCartePlusieursfoIs(){
        joueur.recevoirCarte(carteAs);
        joueur.recevoirCarte(carteRoi);
        joueur.recevoirCarte(carte10);
        
        Carte carte1 = joueur.jouerCarte();
        assertNotNull(carte1);
        assertEquals(2, joueur.getMain().nbr_carte());
        
        Carte carte2 = joueur.jouerCarte();
        assertNotNull(carte2);
        assertEquals(1, joueur.getMain().nbr_carte());
        
        Carte carte3 = joueur.jouerCarte();
        assertNotNull(carte3);
        assertEquals(0, joueur.getMain().nbr_carte());
        assertTrue(joueur.getMain().estVide());
    }
    
    /**
     * Tests avec plusieurs cartes ajoutées
     */
    @Test
    public void testJouerCarteDifferentesCartes(){
        for(int i = 0; i < 5; i++){
            joueur.recevoirCarte(new Carte("" + i, "Pique"));
        }
        
        Carte jouee1 = joueur.jouerCarte();
        Carte jouee2 = joueur.jouerCarte();
        
        assertNotNull(jouee1);
        assertNotNull(jouee2);
    }
    
    /**
     * Tests avec la méthode ToString
     */
    
    @Test
    public void testToString(){
        String result = joueur.toString();
        assertTrue(result.contains("bot"));
        assertTrue(result.contains("IA"));
        assertTrue(result.contains("main"));
    }
    
    @Test
    public void testToStringAvecCartes(){
        joueur.recevoirCarte(carteAs);
        String result = joueur.toString();
        assertTrue(result.contains("IA"));
    }
    
    
    @Test
    public void testMainVide(){
        JoueurIA ia = new JoueurIA("Test");
        assertTrue(ia.getMain().estVide());
        assertEquals(0, ia.getMain().nbr_carte());
    }
    
    @Test
    public void testRecevoirCartesMultiples(){
        for(int i = 0; i < 10; i++){
            joueur.recevoirCarte(new Carte("" + i, "Pique"));
        }
        assertEquals(10, joueur.getMain().nbr_carte());
        for(int i = 0; i < 5; i++){
            Carte jouee = joueur.jouerCarte();
            assertNotNull(jouee);
        }
        assertEquals(5, joueur.getMain().nbr_carte());
    }
    
    
    /**
     * Tests sur plusieurs cartes jouees
     */
    @Test
    public void testJouerToutesLesCartes(){
        joueur.recevoirCarte(carteAs);
        joueur.recevoirCarte(carteRoi);
        joueur.recevoirCarte(carte10);
        
        joueur.jouerCarte();
        joueur.jouerCarte();
        joueur.jouerCarte();
        
        assertTrue(joueur.getMain().estVide());
    }
    
    @Test
    public void testJouerCarteAleatoire(){
        joueur.recevoirCarte(new Carte("As", "Pique"));
        joueur.recevoirCarte(new Carte("2", "Coeur"));
        joueur.recevoirCarte(new Carte("3", "Carreau"));
        joueur.recevoirCarte(new Carte("4", "Trefle"));
        joueur.recevoirCarte(new Carte("5", "Pique"));
        
        Carte jouee = joueur.jouerCarte();
        
        assertNotNull(jouee);
        assertTrue(jouee.hauteur.equals("As") || jouee.hauteur.equals("2") || jouee.hauteur.equals("3") || jouee.hauteur.equals("4") || jouee.hauteur.equals("5"));
    }
}