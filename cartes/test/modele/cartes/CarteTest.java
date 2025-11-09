package modele.cartes;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tellier212@CAMPUS
 */
public class CarteTest {
    
    public CarteTest() {
    }
    
    @Test
    /**
     * Test pour la méthode toString de Carte
     */
    public void testToString() {
        Carte c = new Carte("As", "Pique");
        assertEquals("Carte : As de ♠", c.toString());
    }

    @Test
    /**
     * Test pour la récupération du symbole d'une carte
     */
    public void testGetSymboleCouleur() {
        Carte c1 = new Carte("Roi", "Pique");
        Carte c2 = new Carte("Dame", "Coeur");
        Carte c3 = new Carte("10", "Carreau");
        Carte c4 = new Carte("2", "Trefle");
        Carte c5 = new Carte("7", "");

        assertEquals("♠", c1.getSymboleCouleur());
        assertEquals("♥", c2.getSymboleCouleur());
        assertEquals("♦", c3.getSymboleCouleur());
        assertEquals("♣", c4.getSymboleCouleur());
        assertEquals("", c5.getSymboleCouleur());
    }

    @Test
    /**
     * Test pour la comparaison entre deux cartes
     * Comparaison réalisée avec HashCode et equals
     */
    public void testEqualsEtHashCode() {
        Carte c1 = new Carte("As", "Coeur");
        Carte c2 = new Carte("As", "Coeur");
        Carte c3 = new Carte("Roi", "Pique");

        assertTrue(c1.equals(c2));
        assertEquals(c1.hashCode(), c2.hashCode());

        assertFalse(c1.equals(c3));
        assertNotEquals(c1.hashCode(), c3.hashCode());
    }
}
