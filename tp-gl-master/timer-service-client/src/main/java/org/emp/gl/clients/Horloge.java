package org.emp.gl.clients;

// 1. Importer le nouvel événement
import java.beans.PropertyChangeEvent;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class Horloge implements TimerChangeListener {

    // ... (constructeur ne change pas) ...
    String name;
    TimerService timerService;

    public Horloge(String name, TimerService timerService) {
        this.name = name;
        this.timerService = timerService;
        this.timerService.addTimeChangeListener(this);
        System.out.println("Horloge " + name + " initialized!");
    }

    // 2. Remplacer l'ancienne méthode par la nouvelle
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // 3. Récupérer le nom de la propriété depuis l'ÉVÉNEMENT
        String prop = evt.getPropertyName();

        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            
            System.out.println(name + " affiche " +
                    timerService.getHeures() + ":" +
                    timerService.getMinutes() + ":" +
                    timerService.getSecondes());
        }
    }
}