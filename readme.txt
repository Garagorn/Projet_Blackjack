Application Java permettant de jouer au Blackjack, avec compilation et exécution via Ant.
	
0) Logiciel utilisé (Prérequis) :
	Java 
	Ant 

1)Pour commencer  

Si vous souhaitez lancer directement le jeux de blackjack vous pouvez dans un terminal effectuer la commande $ ant.

/thomas-siaghi-tellier/blackjack$ ant

ou  pour lancer la simulation des cartes:

/thomas-siaghi-tellier/cartes$ ant

En lançant cette commande le build.xml va effectuer la compilation puis exécuter et lancer  le démonstrateur.

Si vous souhaitez seulement compiler le code : 
	/thomas-siaghi-tellier/blackjack$ ant compile
	ou
	/thomas-siaghi-tellier/cartes$  ant compile
	
Si vous souhaitez seulement éxécuter le code après avoir compiler : 
	/thomas-siaghi-tellier/blackjack$ ant run
	ou
	/thomas-siaghi-tellier/cartes$  ant run
	
Si vous souhaitez supprimer la javadoc et le fichier de build : 
	/thomas-siaghi-tellier/blackjack$ ant clean
	ou
	/thomas-siaghi-tellier/cartes$  ant clean

Une fois la commande ant lancée ou la compilation et exécution le jeu de blackjack/simulation de cartes se lanceras.

2) Lancer les test
Si vous souhaitez tester les differentes classe des cartes :
    i) dans /thomas-siaghi-tellier/cartes effectuer /thomas-siaghi-tellier/cartes$ ant junitreport.
    
    ii) rendez vous désormais dans le dossier thomas-siaghi-tellier/cartes/RapportTest ou vous trouverez le fichier index.html qui vous enverras sur un rapport des test.
    
Si vous souhaitez tester les différentes classe du blackjack :
	i)  dans /thomas-siaghi-tellier/cartes effectuer /thomas-siaghi-tellier/cartes$  ant compile.
	
	ii) dans /thomas-siaghi-tellier/blackjack effectuer /thomas-siaghi-tellier/blackjack$ ant junitreport.
	
	iii) rendez vous désormais dans le dossier thomas-siaghi-tellier/blackjack/RapportTest ou vous trouverez le fichier index.html qui vous enverras sur un rapport des test.
	
3) Rapport des test 
    - Vous trouverez un premier tableaux "Summary" qui résume l'entièreté des test avec le pourcentage de réussite de ceci.
    - En dessous de celui ci vous trouverez les réussites par packages sur lesquelles vous pouvez cliquer afin d'avoir le détail pour chaque classes contenue dans le package.
    
    
Pour plus de détail sur l'architecture se référer au rapport fourni.

Auteur :
THOMAS Matthieu
TELLIER Basile 
SIAGHI Massinissa
