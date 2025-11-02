/*
 * ... (commentaires de licence) ...
 */
package org.emp.gl.time.service.impl;

// 1. Importer les classes de 'java.beans'
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import java.time.LocalTime;
// 2. Supprimer les imports de LinkedList et List
import java.util.Timer;
import java.util.TimerTask;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

/**
 *
 * @author tina
 */
public class DummyTimeServiceImpl
        implements TimerService {

    int dixiemeDeSeconde;
    int minutes;
    int secondes;
    int heures;
    
    // 3. Remplacer la LinkedList par PropertyChangeSupport
    // List<TimerChangeListener> listeners = new LinkedList<>();
    private final PropertyChangeSupport support;

    /**
     * Constructeur...
     */
    public DummyTimeServiceImpl() {
        // 4. Initialiser le support
        this.support = new PropertyChangeSupport(this);
        
        setTimeValues();
        // ... (le reste du constructeur Timer/TimerTask ne change pas) ...
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
             @Override
            public void run() {
                timeChanged();
            }
        };
        timer.scheduleAtFixedRate(task, 100, 100);
    }

    private void setTimeValues() {
        // ... (cette méthode ne change pas) ...
        LocalTime localTime = LocalTime.now();

        setSecondes(localTime.getSecond());
        setMinutes(localTime.getMinute());
        setHeures(localTime.getHour());
        setDixiemeDeSeconde(localTime.getNano() / 100000000);
    }

   
    // 5. Modifier les méthodes d'ajout/suppression
    @Override
    public void addTimeChangeListener(TimerChangeListener pl) {
        // Déléguer au support
        support.addPropertyChangeListener(pl);
    }

    @Override
    public void removeTimeChangeListener(TimerChangeListener pl) {
        // Déléguer au support
        support.removePropertyChangeListener(pl);
    }

    private void timeChanged() {
        setTimeValues();
    }

    public void setDixiemeDeSeconde(int newDixiemeDeSeconde) {
        if (dixiemeDeSeconde == newDixiemeDeSeconde)
            return;
        int oldValue = dixiemeDeSeconde;
        dixiemeDeSeconde = newDixiemeDeSeconde;
        
        // 6. Utiliser 'firePropertyChange' pour notifier
        support.firePropertyChange(TimerChangeListener.DIXEME_DE_SECONDE_PROP, 
                                   oldValue, dixiemeDeSeconde);
    }

    // 7. Supprimer l'ancienne méthode 'dixiemeDeSecondesChanged'
    /*
    private void dixiemeDeSecondesChanged(int oldValue, int newValue) {
       for (TimerChangeListener l : listeners) {
           l.propertyChange(TimerChangeListener.DIXEME_DE_SECONDE_PROP,
                   oldValue, dixiemeDeSeconde);
       }
    }
    */

    public void setSecondes(int newSecondes) {
        if (secondes == newSecondes)
            return;
        int oldValue = secondes;
        secondes = newSecondes;

        // 8. Utiliser 'firePropertyChange' pour notifier
        support.firePropertyChange(TimerChangeListener.SECONDE_PROP, 
                                   oldValue, secondes);
    }

    // 9. Supprimer l'ancienne méthode 'secondesChanged'
    /*
    private void secondesChanged(int oldValue, int secondes) {
       for (TimerChangeListener l : listeners) {
           l.propertyChange(TimerChangeListener.SECONDE_PROP,
                   oldValue, secondes);
       }
    }
    */

    public void setMinutes(int newMinutes) {
        if (minutes == newMinutes)
            return;
        int oldValue = minutes;
        minutes = newMinutes;
        
        // 10. Utiliser 'firePropertyChange' pour notifier
        support.firePropertyChange(TimerChangeListener.MINUTE_PROP, 
                                   oldValue, minutes);
    }
    
    // 11. Supprimer l'ancienne méthode 'minutesChanged'
    // ET CORRIGER LE BUG DANS L'ANCIENNE MÉTHODE (elle passait 'secondes')
    /*
    private void minutesChanged(int oldValue, int minutes) {
       for (TimerChangeListener l : listeners) {
           l.propertyChange(TimerChangeListener.MINUTE_PROP,
                   oldValue, secondes); // <- C'était un bug ici
       }
    }
    */

    public void setHeures(int newHeures) {
        if (heures == newHeures)
            return;
        int oldValue = heures;
        heures = newHeures;
        
        // 12. Utiliser 'firePropertyChange' pour notifier
        support.firePropertyChange(TimerChangeListener.HEURE_PROP, 
                                   oldValue, heures);
    }

    // 13. Supprimer l'ancienne méthode 'heuresChanged'
    // ET CORRIGER LE BUG DANS L'ANCIENNE MÉTHODE (elle passait 'secondes')
    /*
    private void heuresChanged(int oldValue, int heures) {
       for (TimerChangeListener l : listeners) {
           l.propertyChange(TimerChangeListener.HEURE_PROP,
                   oldValue, secondes); // <- C'était un bug ici
       }
    }
    */

    // ... (les méthodes get... ne changent pas) ...
    @Override
    public int getDixiemeDeSeconde() { return dixiemeDeSeconde; }
    @Override
    public int getHeures() { return heures; }
    @Override
    public int getMinutes() { return minutes; }
    @Override
    public int getSecondes() { return secondes; }
}