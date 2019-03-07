# Bilan itération 4

### Bilan de l'itération des fonctionnalités implémentée :

Implémentation d'un jeu simple sur la reconnaissance du dessin. Le serveur demande au client de déssiner une forme aleatoire
(carre, triangle, rond, point, segment) et selon la forme demandé le client doit dessiner le nomdre de traits ou points adéquat.
Lorsqu'un trait est déssiné, il est considéré comme un point.
Le client peut changer la couleur de son crayon, ceci adapte aussi la couleur du thème en conséquence.

Modification des bouttons : Valider (le client envoie le nombre de points déssinés sur le canvas au serveur, et éfface le canvas)
                            Start (le client demande au serveur une nouvelle forme pour commencer a jouer)
                            Effacer (le client éfface le canvas)
                            Couleur (le client change la couleur de son crayon et du theme)
                            
Création d'un TextView indiquant au client le nombre de points qu'il a envoyé au serveur.
Création d'un TextView indiquant au client la réponse du serveur : "Juste","Faux".
                            

Realisation de l'IHM.


### Bilan de l'iteration 5 a venir :
Mise en place d'un système de score / points pour chaque défi de dessin
Mise en place d'un systeme qui perfectionne le tracé d'un segment
Préparations pour la fonctionnalité de reconnaissance de forme geometrique

### Bilan de l'itération sur les tests :
