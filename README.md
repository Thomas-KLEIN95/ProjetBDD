# ProjetBDD

Membres du groupe 5 ayant réalisé le projet java : 
- CHENG Wen-Miin 
- KLEIN Thomas


Nous avons réalisé ce projet de développement à 2. 

## Rôles dans le projet 
- CHENG Wen-Miin : chargement du fichier CSV, les statistiques liées aux recherches, écriture du readme.md. 
- KLEIN Thomas : les fonctionnalités de recherches : séquentielle et par index, ajout de la liaison entre clé et pointeur, effectuer différentes recherches.

## Pour lancer le projet : 
- avoir Maven
- ajouter le jar OpenCSV se trouvant dans ../b-tree/lib/opencsv-5.5.2.jar dans les modules settings (F4 sur IntelliJ)
- reload Maven Project


Fichier de données au format csv situé dans ../b-tree/stream_games.csv contenant plus de 10 000 lignes (plus de 17 000).

## Partie indexation

- Les clefs sont associées à des pointeurs.
- La fonctionnalité qui parcourt de façon séquentielle l'arbre, c'est-à-dire de feuille en feuille pour tout l'arbre a été implémenté.

## Partie recherche

- La fonctionnalité recherche par indexation a été effectuée.
- Une méthode a été faite permettant de réaliser différentes recherches : 100 ou tout autre nombre de valeurs.
- Des statisques ont été calculées pour comparer les deux techniques de recherches : le temps minimum, maximum et moyen.

## Résultats des statistiques effectuées

### Pour 100 valeurs : 
Pour une recherche séquentielle de 100 valeurs, le temps minimum est de 4000 nanosecondes, le temps maximum est de 378800 nanosecondes, et le temps moyen est de 71277 nanosecondes.

Pour une recherche par index de 100 valeurs, le temps minimum est de 700 nanosecondes, le temps maximum est de 10599 nanosecondes, et le temps moyen est de 2100 nanosecondes.

Nous remarquons que la recherche par index est nettement plus rapide que la recherche séquentielle. Thomas a tellement optimisé le code qu'il n'est pas possible d'afficher le temps en millisecondes. :) #Wen 

### Pour 10 000 valeurs : 
Pour une recherche séquentielle de 10 000 valeurs, le temps minimum est de 301 nanosecondes, le temps maximum est de 2573700 nanosecondes, et le temps moyen est de 97948 nanosecondes.

Pour une recherche par index de 10 000 valeurs, le temps minimum est de 199 nanosecondes, le temps maximum est de 121100 nanosecondes, et le temps moyen est de 911 nanosecondes.

Nous avons testé pour 10 000 valeurs, la recherche par index est toujours nettement plus rapide que la recherche séquentielle. Etant donné que notre fichier contient un peu plus de 17 000 lignes, la recherche a plus de chance de tomber sur des clés qui se positionnent plus haut dans l'arbre. 
