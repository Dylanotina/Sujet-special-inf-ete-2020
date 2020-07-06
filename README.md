# Sujet-special-inf-ete-2020

# Configuration
Le projet se déroule avec 2 coeurs, 1 worker et 1 driver en Local.
J'ai travaillé sur un MacBook Air de 2015 donc une config assez vieille, il est possible de changer la config pour améliorer la performance du programme.

# Lancement du programme
On a un premier service qui permet de récupérer les données et de les sauvegarder dans des fichiers csv et un autre service qui permet d'avoir une sortie des données.
Il faut d'abord lancer le service StreamingJob.java et le laisser tourner pour pendant un moment pour avoir un maximum de données pour l'analyse après.
Ensuite, il faut lancer le service Analyse.java qui va nous permettre d'afficher les données et de nous permettre de les analyser.
Pour cela j'ai crée un Launcher.java qui va lancer ces deux classes.

# Sécurité

Le programme demande une authentification à Github en créant une clé personnelle.
Celle que j'ai placé dans le code envoyé n'est plus valable, il faut la changer et la remplacer par une nouvelle clé. 
