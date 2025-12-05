package blackjack.modele.joueurs;

import blackjack.modele.actions.Action;
import blackjack.modele.strategie.StrategieJeu;
import modele.cartes.Carte;
import modele.cartes.Paquet;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class JoueurIATest {
    
    private JoueurIA joueurIA;
    private StrategieJeuMock strategieMock;
    
    /**
     * Utiliser les Strategies sans les implemetées
     */
    private static class StrategieJeuMock implements StrategieJeu {
        private Action actionARetourner;
        
        public void setActionARetourner(Action action) {
            this.actionARetourner = action;
        }
        
        @Override
        public Action choisirAction(Main main, Carte carteVisibleCroupier, 
                                    List<Action> actionsDisponibles) {
            return actionARetourner != null ? actionARetourner : 
                   (actionsDisponibles.isEmpty() ? null : actionsDisponibles.get(0));
        }
        
        @Override
        public String getNom() {
            return "StrategieMock";
        }
    }
    
    @Before
    public void setUp() {
        strategieMock = new StrategieJeuMock();
        joueurIA = new JoueurIA("Robot", 1000, strategieMock);
    }   
    
    @Test
    public void testConstructeur() {
        assertEquals("Robot", joueurIA.getNom()); //Meme nom
        assertEquals(1000, joueurIA.getJetons()); //Bon nombre de jetons
        assertNotNull(joueurIA.getStrategie()); //Conaitre sa stratégie
        assertEquals("StrategieMock", joueurIA.getStrategie().getNom()); //Bonne strategie ppour le nom  du joueur
    }
    
    @Test
    public void testEstHumain() {
        assertFalse(joueurIA.estHumain());
    }

/**
 * Tests sur les mises de l'IA
 */
    @Test
    public void testDeterminerMise5PourcentJetons() {
        int mise = joueurIA.determinerMise(10);
        
        assertEquals(50, mise);
    }
    @Test
    public void testDeterminerMiseRespecteMiseMinimale() {
        joueurIA.setJetons(100);
        int mise = joueurIA.determinerMise(50);

        assertEquals(50, mise);
    }
    @Test
    public void testDeterminerMiseNePasDepasserJetons() {
        joueurIA.setJetons(30);
        int mise = joueurIA.determinerMise(50);

        assertEquals(30, mise);
    }
    @Test
    public void testDeterminerMiseMiseMinimaleZero() {
        int mise = joueurIA.determinerMise(0);

        assertEquals(50, mise);
    }
    @Test
    public void testDeterminerMiseJetonsInsuffisants() {
        joueurIA.setJetons(20);
        int mise = joueurIA.determinerMise(100);
        
        assertEquals(20, mise);
    }

/**
 * Tests sur la strategie
 */    
    @Test
    public void testGetStrategie() {
        assertEquals(strategieMock, joueurIA.getStrategie());
    }   
    @Test
    public void testToStringSansStrategie() {
        joueurIA.changerStrategie(null);
        String result = joueurIA.toString();
        
        assertTrue(result.contains("[IA - Aucune]"));
    }
    
        
    @Test
    public void testMultiplesMainsIA() {
        joueurIA.demarrerNouvelleMain(100);
        Main deuxiemeMain = new Main(100);
        joueurIA.ajouterMain(deuxiemeMain);
        
        assertEquals(2, joueurIA.getNombreMains());
        assertTrue(joueurIA.aUneDeuxiemeMain());
    }
    
    @Test
    public void testGestionBudgetIA() {
        // Tester plusieurs mises consécutives
        int miseInitiale = joueurIA.determinerMise(10);
        joueurIA.debiterJetons(miseInitiale);
        
        int miseSeconde = joueurIA.determinerMise(10);

        assertTrue(miseSeconde <= joueurIA.getJetons() + miseSeconde);
    }
    
    @Test
    public void testIAAvecJetonsZero() {
        joueurIA.setJetons(0);
        int mise = joueurIA.determinerMise(10);
        
        assertEquals(0, mise);
        assertFalse(joueurIA.peutMiser(10));
    }
        
    @Test
    public void testDeterminerMiseAvecJetonsEleves() {
        joueurIA.setJetons(10000);
        int mise = joueurIA.determinerMise(10);
        
        assertEquals(500, mise);
    }
    
}