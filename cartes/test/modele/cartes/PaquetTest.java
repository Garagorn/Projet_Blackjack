package modele.cartes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author tellier212@CAMPUS
 */
public class PaquetTest {
    
    public PaquetTest() {
    }
    
    //ant junitreport
    
    private static Carte PA;
    private static Carte C2;
    private static Paquet p;
    private static Paquet p2;
    
    @BeforeClass
    public static void setUpClass() {
        PA = new Carte("As", "Pique");
        C2 = new Carte("2", "Coeur");

        p = new Paquet();
        p2 = new Paquet();
        p2.ajouter(PA);
    }
    
    @AfterClass
    public static void tearDownClass() {
        p = null;
        p2 = null;
        PA = null;
        C2 = null;
    }
    
    @Test
    public void testGetPaquet() {
        Paquet p = new Paquet();
        Carte c1 = new Carte("As", "Pique");
        Carte c2 = new Carte("Roi", "Coeur");

        p.ajouter(c1);
        p.ajouter(c2);

        assertEquals(2, p.getPaquet().size());

        assertTrue(p.getPaquet().contains(c1));
        assertTrue(p.getPaquet().contains(c2));

        p.getPaquet().remove(c1);
        assertEquals(1, p.nbr_carte());
    }
    
    @Test
    public void testAjouterEtRetirer(){
        Paquet test = new Paquet();
        test.ajouter(C2);
        assertTrue(test.contient(C2));
        test.retirer(C2);
        assertFalse(test.contient(C2));
    }
    
    @Test
    public void testNombreCarte(){
        assertEquals(1,p2.nbr_carte());
        assertEquals(0,p.nbr_carte());
    }
    
    @Test
    public void testEstVide(){
        assertTrue(p.estVide());
        assertFalse(p2.estVide());
    }
    
    @Test
    public void testContient(){
        assertTrue(p2.contient(PA));
        assertFalse(p2.contient(C2));
    }
    
    @Test
    public void testCreer32et52(){
        Paquet jeu32 = Paquet.creerJeu32();
        Paquet jeu52 = Paquet.creerJeu52();

        assertEquals(32, jeu32.nbr_carte());
        assertEquals(52, jeu52.nbr_carte());
    }
    
}
