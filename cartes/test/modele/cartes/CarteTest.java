package modele.cartes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tellier212@CAMPUS
 */
public class CarteTest {
    
    private Carte carteAs;
    private Carte carteRoi;
    private Carte carte10;
    
    @Before
    public void setUp(){
        carteAs = new Carte("As", "Pique");
        carteRoi = new Carte("Roi", "Coeur");
        carte10 = new Carte("10", "Carreau");
    }
    
    @Test
    public void testConstructeur(){
        Carte carte = new Carte("Dame", "Trefle");
        
        assertEquals("Dame", carte.hauteur);
        assertEquals("Trefle", carte.couleur);
    }
    
    @Test
    public void testConstructeurAllHauteurs(){
        String[] hauteurs = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
                            "Valet", "Dame", "Roi"};
        
        for (String hauteur : hauteurs) {
            Carte carte = new Carte(hauteur, "Pique");
            assertEquals(hauteur, carte.hauteur);
            assertEquals("Pique", carte.couleur);
        }
    }
    
    @Test
    public void testConstructeurAllCouleurs(){
        String[] couleurs = {"Pique", "Coeur", "Carreau", "Trefle"};
        for (String couleur : couleurs){
            Carte carte = new Carte("As", couleur);
            assertEquals("As", carte.hauteur);
            assertEquals(couleur, carte.couleur);
        }
    }
    
    /**
     * Tests sur les différentes couleurs
     */
    @Test
    public void testGetSymbolePique(){
        Carte carte = new Carte("As", "Pique");
        assertEquals("♠", carte.getSymboleCouleur());
    }
    
    @Test
    public void testGetSymboleCoeur(){
        Carte carte = new Carte("Roi", "Coeur");
        assertEquals("♥", carte.getSymboleCouleur());
    }
    
    @Test
    public void testGetSymboleCarreau(){
        Carte carte = new Carte("10", "Carreau");
        assertEquals("♦", carte.getSymboleCouleur());
    }
    
    @Test
    public void testGetSymboleTrefle(){
        Carte carte = new Carte("2", "Trefle");
        assertEquals("♣", carte.getSymboleCouleur());
    }
    
    @Test
    public void testGetSymboleInconnue(){
        Carte carte = new Carte("As", "Inconnu");
        assertEquals("", carte.getSymboleCouleur());
    }

    @Test
    public void testGetSymboleVide(){
        Carte carte = new Carte("As", "");
        assertEquals("", carte.getSymboleCouleur());
    }
    
    @Test
    public void testGetSymboleIncorrect(){
        Carte carte1 = new Carte("As", "pique");
        Carte carte2 = new Carte("As", "PIQUE");
        assertEquals("", carte1.getSymboleCouleur());
        assertEquals("", carte2.getSymboleCouleur());
    }
    
    
    /**
     * Tests sur la méthode ToString de Carte
     */
    
    @Test
    public void testToStringAsPique(){
        assertEquals("Carte : As de ♠", carteAs.toString());
    }
    
    @Test
    public void testToStringRoiCoeur(){
        assertEquals("Carte : Roi de ♥", carteRoi.toString());
    }
    
    @Test
    public void testToString10Carreau(){
        assertEquals("Carte : 10 de ♦", carte10.toString());
    }
    
    @Test
    public void testToStringDameTrefle(){
        Carte carte = new Carte("Dame", "Trefle");
        assertEquals("Carte : Dame de ♣", carte.toString());
    }
    
    @Test
    public void testToStringAvecCouleurInconnue(){
        Carte carte = new Carte("As", "Inconnu");
        assertEquals("Carte : As de ", carte.toString());
    }
    
    @Test
    public void testToStringToutesLesHauteurs(){
        String[] hauteurs = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
                            "Valet", "Dame", "Roi"};
        
        for (String hauteur : hauteurs) {
            Carte carte = new Carte(hauteur, "Pique");
            assertTrue(carte.toString().contains(hauteur));
            assertTrue(carte.toString().contains("♠"));
        }
    }
    
    /**
     * Tests sur la méthode Equals de CArte
     */
    
    @Test
    public void testEqualsSameCartes(){
        Carte carte1 = new Carte("As", "Coeur");
        Carte carte2 = new Carte("As", "Coeur");
        assertTrue(carte1.equals(carte2));
        assertTrue(carte2.equals(carte1));
    }
    
    @Test
    public void testEqualsCartesDiffHauteur(){
        Carte carte1 = new Carte("As", "Coeur");
        Carte carte2 = new Carte("Roi", "Coeur");
        assertFalse(carte1.equals(carte2));
    }
    
    @Test
    public void testEqualsCartesDiffCouleur(){
        Carte carte1 = new Carte("As", "Coeur");
        Carte carte2 = new Carte("As", "Pique");
        assertFalse(carte1.equals(carte2));
    }
    
    @Test
    public void testEqualsCartesDifferentes(){
        Carte carte1 = new Carte("As", "Coeur");
        Carte carte2 = new Carte("Roi", "Pique");
        assertFalse(carte1.equals(carte2));
    }
    
    @Test
    public void testEqualsSameObjet(){
        assertTrue(carteAs.equals(carteAs));
    }
    
    @Test
    public void testEqualsNull(){
        assertFalse(carteAs == null);
    }

    
    /**
     * Tests les méthodes de HashCode de Carte
     */
    
    @Test
    public void testHashCodeSameCarte(){
        Carte carte1 = new Carte("As", "Coeur");
        Carte carte2 = new Carte("As", "Coeur");
        assertEquals(carte1.hashCode(), carte2.hashCode());
    }
    
    @Test
    public void testHashCodeDiffCartes(){
        Carte carte1 = new Carte("As", "Coeur");
        Carte carte2 = new Carte("Roi", "Pique");
        assertNotEquals(carte1.hashCode(), carte2.hashCode());
    }
    
    @Test
    public void testHashCodeMemeObjet(){
        assertEquals(carteAs.hashCode(), carteAs.hashCode());
    }
    
    @Test
    public void testHashCode(){
        int hash1 = carteAs.hashCode();
        int hash2 = carteAs.hashCode();
        assertEquals(hash1, hash2);
    }
}

