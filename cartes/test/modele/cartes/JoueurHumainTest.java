package modele.cartes;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tellier212@CAMPUS
 */
public class JoueurHumainTest {
    
    public JoueurHumainTest(){
    }
        
    @Test
    /**
     * Test pour la récupération des noms des joueurs
     */
    public void testGetNom(){
        JoueurHumain ja = new JoueurHumain("j1");
        JoueurHumain jb = new JoueurHumain("j2");
        assertEquals("j1", ja.getNom());
        assertNotEquals("", jb.getNom());
    }    
    
    @Test
    /**
     * Test sur les mains des joueurs
     */
    public void testGetMain(){
        JoueurHumain ja = new JoueurHumain("j1");
        Carte c = new Carte("As", "Pique");
        ja.recevoirCarte(c);
        
        assertTrue(ja.getMain().contient(c));
        assertFalse(ja.getMain().estVide());
    
        JoueurHumain jb = new JoueurHumain("j2");
        assertTrue(jb.getMain().estVide());
        assertFalse(jb.getMain().contient(c));
    }    
}
