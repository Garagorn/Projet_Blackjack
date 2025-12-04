package modele.cartes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tellier212@CAMPUS
 */
public class JoueurHumainTest {
    
    private JoueurHumain joueur;
    private Carte carteAs;
    private Carte carteRoi;
    private Carte carte10;
    
    @Before
    public void setUp(){
        joueur = new JoueurHumain("Toto");
        carteAs = new Carte("As", "Pique");
        carteRoi = new Carte("Roi", "Coeur");
        carte10 = new Carte("10", "Carreau");
    }
    
    /**
     * Tests sur deux types de constructeur
     */
    @Test
    public void testConstructeur(){
        JoueurHumain j = new JoueurHumain("Toto");
        
        assertEquals("Toto", j.getNom());
        assertNotNull(j.getMain());
        assertTrue(j.getMain().estVide());
    }
    
    @Test
    public void testConstructeurNomVide(){
        JoueurHumain j = new JoueurHumain("");
        
        assertEquals("", j.getNom());
        assertNotNull(j.getMain());
    }
    
    /**
     * Tests de get plusieurs noms
     */
    @Test
    public void testGetNom(){
        assertEquals("Toto", joueur.getNom());
    }
    
    @Test
    public void testGetNomDifferentsJoueurs(){
        JoueurHumain j1 = new JoueurHumain("Joueur1");
        JoueurHumain j2 = new JoueurHumain("Joueur2");
        
        assertEquals("Joueur1", j1.getNom());
        assertEquals("Joueur2", j2.getNom());
        assertNotEquals(j1.getNom(), j2.getNom());
    }
    
    /**
     * Tests de get plusieurs mains
     */
    @Test
    public void testGetMain(){
        assertNotNull(joueur.getMain());
        assertTrue(joueur.getMain().estVide());
    }
    
    @Test
    public void testGetMainNonNull(){
        JoueurHumain j = new JoueurHumain("Test");
        assertNotNull(j.getMain());
    }
    
    
    /**
     * Test de la méthode ToString
     */
    @Test
    public void testToString(){
        String result = joueur.toString(); 
        assertTrue(result.contains("joueur humain"));
        assertTrue(result.contains("Toto"));
        assertTrue(result.contains("main"));
    }
  
    /**
     * Tests sur différentes mains
     */
    @Test
    public void testMainIndependante(){
        JoueurHumain j1 = new JoueurHumain("J1");
        JoueurHumain j2 = new JoueurHumain("J2");
        j1.recevoirCarte(carteAs);
        
        assertTrue(j1.getMain().contient(carteAs));
        assertFalse(j2.getMain().contient(carteAs));
    }
    
    @Test
    public void testMainsIndependantes(){
        JoueurHumain j1 = new JoueurHumain("J1");
        JoueurHumain j2 = new JoueurHumain("J2");
        j1.recevoirCarte(carteAs);
        j2.recevoirCarte(carteRoi);
        
        assertEquals(1, j1.getMain().nbr_carte());
        assertEquals(1, j2.getMain().nbr_carte());
        
        assertTrue(j1.getMain().contient(carteAs));
        assertFalse(j1.getMain().contient(carteRoi));
        assertTrue(j2.getMain().contient(carteRoi));
        assertFalse(j2.getMain().contient(carteAs));
    }  
    
    @Test
    public void testMainVide(){
        JoueurHumain j = new JoueurHumain("Test");
        
        assertTrue(j.getMain().estVide());
        assertEquals(0, j.getMain().nbr_carte());
    }
    
    /**
     * Tests sur l'ajout de carte 
     */
    @Test
    public void testRecevoirCartesMultiples(){
        for(int i = 0; i < 10; i++){
            joueur.recevoirCarte(new Carte("" + i, "Pique"));
        }
        
        assertEquals(10, joueur.getMain().nbr_carte());
    }
    @Test
    public void testRecevoirUneCarte(){
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
}