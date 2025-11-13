/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package modele.cartes;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tellier212@CAMPUS
 */
public class JoueurIATest {
    
    public JoueurIATest() {
    }
    
    @Test
    /**
     * Test pour la récupération des noms des joueurs
     */
    public void testGetNom(){
        JoueurIA ja = new JoueurIA("j1");
        JoueurIA jb = new JoueurIA("j2");
        assertEquals("j1", ja.getNom());
        assertNotEquals("", jb.getNom());
    }    
    
    @Test
    /**
     * Test sur les mains des joueurs
     */
    public void testGetMain(){
        JoueurIA ja = new JoueurIA("j1");
        Carte c = new Carte("As", "Pique");
        ja.recevoirCarte(c);
        
        assertTrue(ja.getMain().contient(c));
        assertFalse(ja.getMain().estVide());
    
        JoueurIA jb = new JoueurIA("j2");
        assertTrue(jb.getMain().estVide());
        assertFalse(jb.getMain().contient(c));
    }
}
