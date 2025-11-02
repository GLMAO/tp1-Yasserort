package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities; // Très important
import java.awt.BorderLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;

/**
 * Une horloge graphique qui s'abonne au TimerService.
 * Elle hérite de JFrame pour être une fenêtre.
 * Elle implémente TimerChangeListener pour être un observer.
 */
public class HorlogeGraphique extends JFrame implements TimerChangeListener {

    private TimerService timerService;
    private JLabel timeLabel; // L'étiquette pour afficher l'heure

    public HorlogeGraphique(String title, TimerService service) {
        super(title); // Titre de la fenêtre
        this.timerService = service;

        // 1. Mettre en place l'interface graphique
        setupUI();

        // 2. S'abonner au service
        this.timerService.addTimeChangeListener(this);

        // 3. Afficher l'heure initiale
        updateTime(); 
    }

    private void setupUI() {
        // Créer l'étiquette (label) pour l'heure
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Créer un panneau principal et y ajouter le label
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(timeLabel, BorderLayout.CENTER);

        // Configurer la fenêtre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        this.setSize(300, 150); // Taille de la fenêtre
        this.setLocationRelativeTo(null); // Centrer à l'écran
        this.setVisible(true); // Rendre la fenêtre visible
    }

    // Cette méthode met à jour le texte du label
    private void updateTime() {
        // Formatage de l'heure
        String currentTime = String.format("%02d:%02d:%02d",
                timerService.getHeures(),
                timerService.getMinutes(),
                timerService.getSecondes());

        timeLabel.setText(currentTime);
    }

    // 4. Implémenter la méthode de l'observer
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // On ne réagit qu'au changement de seconde
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {

            // IMPORTANT : Les mises à jour de Swing (GUI)
            // doivent TOUJOURS être faites sur l'Event Dispatch Thread (EDT).
            // Notre timer tourne sur un thread séparé.
            // SwingUtilities.invokeLater place notre mise à jour
            // dans la file d'attente de l'EDT pour une exécution sécurisée.

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    updateTime();
                }
            });
        }
    }
}