# Sujet-special-inf-ete-2020

# Configuration
Le projet se déroule avec 2 coeurs, 1 worker et 1 driver.
J'ai travaillé sur un MacBook Air de 2015 donc une config assez vieille, il est possible de changer la config pour améliorer la performance du programme.

# Lancement du programme
On a un premier service qui permet de récupérer les données et de les sauvegarder dans des fichiers csv et un autre service qui permet d'avoir une sortie des données.
Il faut d'abord lancer le service StreamingJob.java et le laisser tourner pour pendant un moment pour avoir un maximum de données pour l'analyse après.
Ensuite, il faut lancer le service Analyse.java qui va nous permettre d'afficher les données et de nous permettre de les analyser.
