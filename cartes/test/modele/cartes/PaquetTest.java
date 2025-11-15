package modele.cartes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.*;
/**
 *
 * @author tellier212@CAMPUS
 */
public class PaquetTest {
    
    public PaquetTest() {
    }
    
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
    
    /**
     * Test sur la factory methode de création  de  carte
     */
    @Test
    public void testCreer32et52(){
        Paquet jeu32 = Paquet.creerJeu32();
        Paquet jeu52 = Paquet.creerJeu52();

        assertEquals(32, jeu32.nbr_carte());
        assertEquals(52, jeu52.nbr_carte());
    }
    
    
    /**
     * Test sur le melange d'un paquet
     * 1) Nombre égal de carte
     * 2) Possede les memes cartes et pas de pertes
     * 3) Changement de l'odre des cartes
     * 
     * 4) Essai avec un 2e melange
     */
    @Test 
    public void testMelange(){
        Paquet jeu32 = Paquet.creerJeu32();
        Paquet jeuCopie = Paquet.creerJeu32();
        jeu32.melanger();
        assertEquals(jeu32.nbr_carte(),jeuCopie.nbr_carte()); //Pas de perte de classe
        
        boolean memes;
        memes = true;
        for(Carte c :jeuCopie.getPaquet()){
            if(!jeu32.contient(c)){
                memes=false;
            }
        }
        assertTrue(memes);
        //Ordre changé
        boolean ordre = true;
        for(int i=0;i<6;i++){
            if (!jeu32.getPaquet().get(i).equals(jeuCopie.getPaquet().get(i))) {
                memes = false;
                break;
            }
        }
        assertFalse(memes);
        
        //Test avec un 2e melange
        jeu32.melanger();
        assertEquals(jeu32.nbr_carte(),jeuCopie.nbr_carte()); //Perte de carte ?
        
        //Test contient les memes cartes
        boolean meme;
        meme = true;
        for(Carte c :jeuCopie.getPaquet()){
            if(!jeu32.contient(c)){
                meme=false;
            }
        }
        assertTrue(meme);
        
        //Test sur les doublons
        Set<Carte> main = new  HashSet<>(jeu32.getPaquet());
        assertEquals(jeu32.nbr_carte(),main.size());
        //Deuxieme melange et test doublon
        jeu32.melanger();
        assertEquals(jeuCopie.nbr_carte(), jeu32.nbr_carte());
        boolean egal;
        egal = true;
        for(Carte c : jeuCopie.getPaquet()){
            if(!jeu32.contient(c)){
                egal=false;
            }
        }
        assertTrue(egal);
    }
    
    //Test sur n itérations
    //@Test  public void testCouperAvance(){}
    
    /**
     * Test sur la coupe d'un paquet
     * 1) Nombre égal de carte
     * 2) Possede les memes cartes et pas de pertes
     * 3) Changement de l'odre des cartes
     * 
     * 4) Essai avec une 2e coupe
     */
    @Test 
    public void testCouper(){
        Paquet jeu32 = Paquet.creerJeu32();
        Paquet jeuCopie = Paquet.creerJeu32();
        jeu32.couper();
        assertEquals(jeu32.nbr_carte(),jeuCopie.nbr_carte()); //Pas de perte de classe
        
        boolean memes;
        memes = true;
        for(Carte c :jeuCopie.getPaquet()){
            if(!jeu32.contient(c)){
                memes=false;
            }
        }
        assertTrue(memes);
        //Ordre changé
        boolean ordre = true;
        for(int i=0;i<6;i++){
            if (!jeu32.getPaquet().get(i).equals(jeuCopie.getPaquet().get(i))) {
                memes = false;
                break;
            }
        }
        assertFalse(memes);
        
        //Test avec une 2e coupe
        jeu32.couper();
        assertEquals(jeu32.nbr_carte(),jeuCopie.nbr_carte()); //Perte de carte ?
        
        //Test contient les memes cartes
        boolean meme;
        meme = true;
        for(Carte c :jeuCopie.getPaquet()){
            if(!jeu32.contient(c)){
                meme=false;
            }
        }
        assertTrue(meme);
        
        //Test sur les doublons
        Set<Carte> main = new  HashSet<>(jeu32.getPaquet());
        assertEquals(jeu32.nbr_carte(),main.size());
        //Deuxieme coupe et test doublon
        jeu32.couper();
        assertEquals(jeuCopie.nbr_carte(), jeu32.nbr_carte());
        boolean egal;
        egal = true;
        for(Carte c : jeuCopie.getPaquet()){
            if(!jeu32.contient(c)){
                egal=false;
            }
        }
        assertTrue(egal);
    }
    
    //@Test  public void testCouperAvance(){}
}
