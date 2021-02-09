# GestionCinema
# Angular + Spring + Thymleaf + MySQL

Gestion des cinémas pour les deux parties front et back office

## Description
 L’objectif était de créer une application web pour gérer les cinémas dans différentes villes (avec Angular; thymleaf et spring).  L’application va permettre d’afficher les villes qui existent dans la base de données (dans notre cas c’est MySQL), les cinémas qui existent dans ces villes, les salles dans ces cinémas, les films et les séances dans chaque salle et les tickets de chaque séance.

## Execution du projet partie back end
* Il faut créer une base de données MySQL sous nom `cinema`.
* Avoir un dossier nomme imagesCinema dans le chemin `C:\Users\${USERNAME}\imagesCinema\images`.

## Output partie back office
![](img/back.png?raw=true)

## Execution du projet partie front end
* Avant de lancer l'application front-end, assurez bien que la partie back-end est bien lancée.
* Si vous rencontrez des problèmes exécutez la commande suivante pour télécharger les dépendances nécessaires pour le projet : `npm install`
* Finalement exécutez `ng serve` pour un serveur de développement, puis accédez à `http: // localhost: 4200 /`.

## Output partie front office
![](img/front.PNG?raw=true)
