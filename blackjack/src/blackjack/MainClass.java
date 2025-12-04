package blackjack;

import blackjack.vue.AppWindows;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe principale de l'application Blackjack.
 * Point d'entrée du programme.
 */
public class MainClass {
    
    /**
     *Constructeur vide
     */
    public MainClass(){
        
    }
    
    /**
     * Point d'entrée de l'application
     * @param args arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // Configurer le Look and Feel du système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Impossible de définir le Look and Feel : " + e.getMessage());
            // Continuer avec le Look and Feel par défaut
        }
        
        // Lancer l'application dans le thread Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                AppWindows application = new AppWindows();
                application.setVisible(true);
                
                System.out.println("=================================");
                System.out.println("   BLACKJACK - Application lancée");
                System.out.println("=================================");
                
            } catch (Exception e) {
                System.err.println("Erreur lors du lancement de l'application : " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}