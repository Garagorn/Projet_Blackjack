package modele.cartes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tellier212@CAMPUS
 */
public class PaquetTest {
    
    private Paquet paquet;
    private Carte carteAs;
    private Carte carteRoi;
    private Carte carte10;
    
    @Before
    public void setUp(){
        paquet = new Paquet();
        carteAs = new Carte("As", "Pique");
        carteRoi = new Carte("Roi", "Coeur");
        carte10 = new Carte("10", "Carreau");
    }
    
    
    @Test
    public void testConstructeur(){
        Paquet p = new Paquet();
        
        assertNotNull(p.getPaquet());
        assertTrue(p.estVide());
        assertEquals(0, p.nbr_carte());
    }
    
    /**
     * Tests d'ajout de carte
     */
    
    @Test
    public void testAjouter(){
        paquet.ajouter(carteAs);
        
        assertEquals(1, paquet.nbr_carte());
        assertTrue(paquet.contient(carteAs));
        assertFalse(paquet.estVide());
    }
    
    @Test
    public void testAjouter3Cartes(){
        paquet.ajouter(carteAs);
        paquet.ajouter(carteRoi);
        paquet.ajouter(carte10);
        
        assertEquals(3, paquet.nbr_carte());
        assertTrue(paquet.contient(carteAs));
        assertTrue(paquet.contient(carteRoi));
        assertTrue(paquet.contient(carte10));
    }
    
    /**
     * Tests pour retirer les cartes
     */
    @Test
    public void testRetirer(){
        paquet.ajouter(carteAs);
        paquet.ajouter(carteRoi);
        paquet.retirer(carteAs);
        
        assertEquals(1, paquet.nbr_carte());
        assertFalse(paquet.contient(carteAs));
        assertTrue(paquet.contient(carteRoi));
    }
    
    @Test
    public void testRetirerJusquaVide(){
        paquet.ajouter(carteAs);
        paquet.retirer(carteAs);
        
        assertTrue(paquet.estVide());
        assertEquals(0, paquet.nbr_carte());
    }
    
    /**
     * Tests d'ajouts spéciaux
     */
    @Test
    public void testAjouterMemeCarte(){
        paquet.ajouter(carteAs);
        paquet.ajouter(carteAs);
        
        assertEquals(2, paquet.nbr_carte());
    }
    
    @Test
    public void testAjouterEtRetirer(){
        paquet.ajouter(carteRoi);
        assertTrue(paquet.contient(carteRoi));
        
        paquet.retirer(carteRoi);
        assertFalse(paquet.contient(carteRoi));
    }
    
    
    /**
     * Tests avec contains
     */
    
    @Test
    public void testContientCarte(){
        paquet.ajouter(carteAs);
        assertTrue(paquet.contient(carteAs));
    }
    
    @Test
    public void testContientPasCarte(){
        paquet.ajouter(carteAs);
        assertFalse(paquet.contient(carteRoi));
    }
    
    @Test
    public void testContientPaquetVide(){
        assertFalse(paquet.contient(carteAs));
    }
    
    
    /**
     * Tests pour le paquet vide
     */
    
    @Test
    public void testEstVidePaquetVide(){
        assertTrue(paquet.estVide());
    }
    
    @Test
    public void testEstVidePaquetNonVide(){
        paquet.ajouter(carteAs);
        assertFalse(paquet.estVide());
    }
    
    /**
     * Test sur la factory methode de création  de  carte
     */
    @Test
    public void testEstVideApresRetrait(){
        paquet.ajouter(carteAs);
        paquet.retirer(carteAs);
        assertTrue(paquet.estVide());
    }
    
    
    /**
     * Tests pour le nombre de cartes dans différentes versions
     */
    
    @Test
    public void testNbrCartePaquetVide(){
        assertEquals(0, paquet.nbr_carte());
    }
    
    @Test
    public void testNbrCarteApresAjouts(){
        paquet.ajouter(carteAs);
        assertEquals(1, paquet.nbr_carte());
        paquet.ajouter(carteRoi);
        assertEquals(2, paquet.nbr_carte());
        paquet.ajouter(carte10);
        assertEquals(3, paquet.nbr_carte());
    }
    
    @Test
    public void testNbrCarteApresRetraits(){
        paquet.ajouter(carteAs);
        paquet.ajouter(carteRoi);
        paquet.ajouter(carte10);
        
        paquet.retirer(carteAs);
        assertEquals(2, paquet.nbr_carte());
        
        paquet.retirer(carteRoi);
        assertEquals(1, paquet.nbr_carte());
    }
    
    
    /**
     * Tests avec la randomisation
     */
    
    @Test
    public void testCarteRandomRetourneCartePresente(){
        paquet.ajouter(carteAs);
        paquet.ajouter(carteRoi);
        paquet.ajouter(carte10);
        
        Carte random = paquet.carteRandom();  
        assertNotNull(random);
        assertTrue(paquet.contient(random));
    }
    
    @Test
    public void testCarteRandomNeSupprimePas(){
        paquet.ajouter(carteAs);
        int tailleAvant = paquet.nbr_carte();
        paquet.carteRandom();
        
        assertEquals(tailleAvant, paquet.nbr_carte());
    }
    
    @Test
    public void testCarteRandomAvecUneSeuleCarte(){
        paquet.ajouter(carteAs);
        assertEquals(carteAs, paquet.carteRandom());
    }
    
    /**
     * Tests sur le melange
     */
    
    @Test
    public void testMelangerMemeTaille(){
        paquet.ajouter(carteAs);
        paquet.ajouter(carteRoi);
        paquet.ajouter(carte10);
        
        int tailleAvant = paquet.nbr_carte();
        paquet.melanger();
        
        assertEquals(tailleAvant, paquet.nbr_carte());
    }
    
    @Test
    public void testMelangerMemesCartes(){
        paquet.ajouter(carteAs);
        paquet.ajouter(carteRoi);
        paquet.ajouter(carte10);
        
        paquet.melanger();
        
        assertTrue(paquet.contient(carteAs));
        assertTrue(paquet.contient(carteRoi));
        assertTrue(paquet.contient(carte10));
    }
    
    @Test
    public void testMelangerPaquetVide(){
        paquet.melanger();
        assertTrue(paquet.estVide());
    }
    
    /**
     * Tests sur la coupe d'un paquet
     */
    
    /**
     * Taille inchangée
     */
    @Test
    public void testCouperMemeTaille(){
        Paquet jeu = Paquet.creerJeu52();
        int tailleAvant = jeu.nbr_carte();
        jeu.couper();
        assertEquals(tailleAvant, jeu.nbr_carte());
    }
    
    /**
     * Le paquet garde toutes ses cartes
     */
    @Test
    public void testCouperGardeToutesLesCartes(){
        paquet.ajouter(carteAs);
        paquet.ajouter(carteRoi);
        paquet.ajouter(carte10);
        Carte carte2 = new Carte("2", "Trefle");
        Carte carte3 = new Carte("3", "Coeur");
        Carte carte4 = new Carte("4", "Pique");
        Carte carte5 = new Carte("5", "Carreau");
        Carte carte6 = new Carte("6", "Trefle");
        paquet.ajouter(carte2);
        paquet.ajouter(carte3);
        paquet.ajouter(carte4);
        paquet.ajouter(carte5);
        paquet.ajouter(carte6);
        
        paquet.couper();
        
        assertTrue(paquet.contient(carteAs));
        assertTrue(paquet.contient(carteRoi));
        assertTrue(paquet.contient(carte10));
        assertTrue(paquet.contient(carte2));
        assertTrue(paquet.contient(carte6));
    }
    
    /**
     * Tests de la  factory methodes d'un paquet de 52
     */
    
    @Test
    public void testCreerJeu52Taille(){
        Paquet jeu = Paquet.creerJeu52();
        assertEquals(52, jeu.nbr_carte());
    }
    
    /**
     * Cartes bien présentes
     */
    @Test
    public void testCreerJeu52Contenu(){
        Paquet jeu = Paquet.creerJeu52();
        
        assertTrue(jeu.contient(new Carte("As", "Pique")));
        assertTrue(jeu.contient(new Carte("Roi", "Coeur")));
        assertTrue(jeu.contient(new Carte("2", "Carreau")));
        assertTrue(jeu.contient(new Carte("10", "Trefle")));
        assertTrue(jeu.contient(new Carte("Dame", "Pique")));
        assertTrue(jeu.contient(new Carte("7", "Carreau")));
        assertTrue(jeu.contient(new Carte("Valet", "Trefle")));
        assertTrue(jeu.contient(new Carte("4", "Coeur")));
        
    }
    
    /**
     * Créer un paquet  contient bien les 4 couleurs
     */
    @Test
    public void testCreerJeu52Couleurs(){
        Paquet jeu = Paquet.creerJeu52();
        int pique = 0, coeur = 0, carreau = 0, trefle = 0;
        for(Carte c : jeu.getPaquet()){
            switch (c.couleur){
                case "Pique": pique++; break;
                case "Coeur": coeur++; break;
                case "Carreau": carreau++; break;
                case "Trefle": trefle++; break;
            }
        }
        assertEquals(13, pique);
        assertEquals(13, coeur);
        assertEquals(13, carreau);
        assertEquals(13, trefle);
    }
    
    /**
     * Créer un paquet  contient bien les 13 cartes par hauteurs
     */
    @Test
    public void testCreerJeu52Hauteurs(){
        Paquet jeu = Paquet.creerJeu52();
        String[] hauteurs = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
                            "Valet", "Dame", "Roi"};
        for(String hauteur : hauteurs){
            int count = 0;
            for(Carte c : jeu.getPaquet()){
                if (c.hauteur.equals(hauteur)) count++;
            }
            assertEquals(4, count);
        }
    }
    
    /**
     * Tests de la  factory methodes d'un paquet de 32
     */
    
    @Test
    public void testCreerJeu32Taille(){
        Paquet jeu = Paquet.creerJeu32();
        assertEquals(32, jeu.nbr_carte());
    }
    

    @Test
    public void testCreerJeu32Contenu(){
        Paquet jeu = Paquet.creerJeu32();

        assertTrue(jeu.contient(new Carte("As", "Pique")));
        assertTrue(jeu.contient(new Carte("7", "Coeur")));
        assertTrue(jeu.contient(new Carte("10", "Carreau")));
        
        //Cartes n'apparteant pas a un paquet de 32
        assertFalse(jeu.contient(new Carte("2", "Pique")));
        assertFalse(jeu.contient(new Carte("6", "Coeur")));
    }
    
    /**
     * Créer un paquet  contient bien les 4 couleurs
     */
    @Test
    public void testCreerJeu32Couleurs(){
        Paquet jeu = Paquet.creerJeu32();
        int pique = 0, coeur = 0, carreau = 0, trefle = 0;
        for(Carte c : jeu.getPaquet()){
            switch (c.couleur){
                case "Pique": pique++; break;
                case "Coeur": coeur++; break;
                case "Carreau": carreau++; break;
                case "Trefle": trefle++; break;
            }
        }
        
        assertEquals(8, pique);
        assertEquals(8, coeur);
        assertEquals(8, carreau);
        assertEquals(8, trefle);
    }
    
    /**
     * Créer un paquet  contient bien les 8 cartes par hauteurs
     */
    @Test
    public void testCreerJeu328Hauteurs(){
        Paquet jeu = Paquet.creerJeu32();
        String[] hauteurs = {"As", "7", "8", "9", "10", "Valet", "Dame", "Roi"};
        for(String hauteur : hauteurs){
            int count = 0;
            for(Carte c : jeu.getPaquet()){
                if (c.hauteur.equals(hauteur)) count++;
            }
            assertEquals(4, count);
        }
    }
    
    /**
     * Tests ToStrinf
     */
    
    @Test
    public void testToStringPaquetVide(){
        String result = paquet.toString();
        assertTrue(result.contains("Paquet"));
        assertTrue(result.contains("[]"));
    }
    
    @Test
    public void testToStringPaquetCartes(){
        paquet.ajouter(carteAs);
        
        String result = paquet.toString();
        assertTrue(result.contains("Paquet"));
    }
    
    /**
     * Test getPaquet
     */
    
    @Test
    public void testGetPaquet(){
        paquet.ajouter(carteAs);
        paquet.ajouter(carteRoi);
        assertEquals(2, paquet.getPaquet().size());
        paquet.getPaquet().remove(carteAs);
        assertEquals(1, paquet.nbr_carte());
    }
    
    @Test
    public void testGetPaquetNonNull(){
        assertNotNull(paquet.getPaquet());
    }
}