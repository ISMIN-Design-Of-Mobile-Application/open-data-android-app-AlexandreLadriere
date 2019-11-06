
## [APP NAME]  
  
Codé par :   
 - Jean-Christophe CHEVALIER   
 - Alexandre LADRIERE   

## Description  
  
URL des données : https://sportplaces.api.decathlon.com/api/v1/places  

Cette application Android fait appel à une base de données qui recense tous les lieux sportifs au monde.
Elle permet ainsi à l'utilisateur de savoir quels lieux à proximité de sa position où aller afin de
pratiquer un sport qu'il renseigne.

Afin de récupérer les données, le client envoie une requête GET au serveur, sous la forme suivante:
_https://sportplaces.api.decathlon.com/api/v1/places?origin=x&radius=y&sports=z_ avec
x: la position actuelle de l'utilisateur.
y: le rayon de recherche en km.
z: le code identifiant le sport indiqué. Chaque sport est associé à un code unique.

## Librairies externes  
  
[A completer]  
  
## Consignes  
  
Lien vers le sujet : https://docs.google.com/presentation/d/1mwu2xx7_qfCZDfsRxseC94n7oBGYfhw-9xIftaTDbzk/edit#slide=id.p97  
  
### But du projet  
  
 - Le but du projet est de réaliser une application permettant de visualiser une série de données OpenData  
 - Les données devront être récupérées sur un serveur distant et affichées dans une liste et sur une carte  
 - Un clique sur un élément de la liste ou sur un marker de la carte permet d’accéder à un écran présentant le détail de l’élément  
 - Un écran présentera des informations générales sur les données récupérées  
  
### Exigences    
- [ ] Format des données OpenData :   
	 - Format Json  
	 - Avec un champ correspondant à l’url d’une image  
	 - Avec un champ correspondant à une position  
 - [ ] Application composée au minimum de 3 Fragment et 2 Activity  
 - [ ] Une Actionbar sera présente et permettra de rafraîchir les données récupérées et affichées
 
 ## Bonus
 
 - Amélioration de l’expérience utilisateur :
    - Clustering des markers sur la carte en fonction du niveau de zoom
    - Mise en place d’un système de recherche/filtre sur la liste affichée
 
 - Enrichissements techniques :
    - Mise en place d’une base de données locale pour afficher la liste d’élément en mode hors connexion
    - Utilisation de LiveData ou d’Observable pour la récupération de données dans la BDD
 
