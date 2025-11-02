[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/t19xNtmg)


IASD30




# TP1 - Génie Logiciel : Le Design Pattern Observer

Ce projet est une implémentation du pattern Observer en utilisant un service de Timer.

## Analyse du Bogue (d.4)

**Question :** L'instanciation de 10 `CompteARebours` engendre des bogues. Pourquoi ?

**Réponse :**
Le bogue est une `java.util.ConcurrentModificationException`. 

Il se produit parce que l'implémentation de base (`DummyTimeServiceImpl`) utilise une `LinkedList` pour gérer les observers. Le service parcourt cette liste (avec une boucle `for-each`) pour notifier chaque observer.
En même temps, un `CompteARebours` qui arrive à 0 tente de se désinscrire en appelant `removeTimeChangeListener()`.

Cela revient à modifier la liste (la supprimer) pendant qu'elle est en cours de parcours, ce qui est interdit par Java et provoque le crash.

## Résolution du Bogue (e)

**Question :** Avez-vous résolu le problème?

**Réponse :**
Oui, le problème est résolu en déléguant la gestion des observers à une instance de `java.beans.PropertyChangeSupport` .

Cette classe est conçue pour être "thread-safe". Elle gère les ajouts et les suppressions d'observers de manière sécurisée, même lorsqu'une notification est en cours. En remplaçant la `LinkedList` manuelle par `PropertyChangeSupport`, les `CompteARebours` peuvent se désinscrire sans provoquer de `ConcurrentModificationException`, et tous les compteurs terminent leur décompte correctement.

## Bonus (f)

Le projet inclut également une horloge graphique (classe `HorlogeGraphique`) réalisée avec Swing, qui s'abonne au `TimerService` pour se mettre à jour à chaque seconde.