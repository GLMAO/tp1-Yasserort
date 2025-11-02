package org.emp.gl.clients;

// 1. Importer le nouvel événement
import java.beans.PropertyChangeEvent;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class CompteARebours implements TimerChangeListener {

    // ... (constructeur ne change pas) ...
    private int compteur;
    private TimerService timerService;

    public CompteARebours(int valeurInitiale, TimerService service) {
        this.compteur = valeurInitiale;
        this.timerService = service;
        this.timerService.addTimeChangeListener(this);
        System.out.println("Nouveau CompteARebours initialisé à " + valeurInitiale);
    }
    
    // 2. Remplacer l'ancienne méthode par la nouvelle
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // 3. Récupérer le nom de la propriété depuis l'ÉVÉNEMENT
        String prop = evt.getPropertyName();
        
        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            
            if (compteur > 0) {
                System.out.println("CompteARebours (" + compteur + ")");
                compteur--;
            } else {
                System.out.println("CompteARebours TERMINÉ. Désinscription.");
                
                // 4. Cette ligne est maintenant SÉCURISÉE (thread-safe)
                this.timerService.removeTimeChangeListener(this);
            }
        }
    }
}