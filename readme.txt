# Projet Blackjack - Java / Ant

Application Java permettant de jouer au Blackjack, avec compilation, exécution et tests via Ant.
	
## Prérequis :
* **Java
* **Ant 

1)Pour commencer  

Si vous souhaitez lancer directement le jeux de blackjack vous pouvez dans un terminal effectuer la commande $ ant.
Depuis un terminal :
```bash
cd/thomas-siaghi-tellier/blackjack
ant
```
ou  pour lancer la simulation des cartes:
```bash
cd /thomas-siaghi-tellier/cartes
ant
```

En lançant cette commande le build.xml va effectuer la compilation puis exécuter et lancer  le démonstrateur.

## Compilation 

### Blackjack

```bash
cd thomas-siaghi-tellier/blackjack
ant compile
```

### Cartes

```bash
cd thomas-siaghi-tellier/cartes
ant compile
```
	
## Exécution

### Blackjack

```bash
cd thomas-siaghi-tellier/blackjack
ant run
```

### Cartes

```bash
cd thomas-siaghi-tellier/cartes
ant run
```

## Nettoyage du projet
### Blackjack

```bash
ant clean
```

### Cartes

```bash
ant clean
```

## Lancer les test
### Tests du module **Cartes**

1. Se placer dans le dossier :

```bash
cd thomas-siaghi-tellier/cartes
```

2. Lancer les tests :

```bash
ant junitreport
```

3. Ouvrir le rapport :

```
thomas-siaghi-tellier/cartes/RapportTest/index.html
```

---

### Tests du module **Blackjack**

1. Compiler le module Cartes :

```bash
cd thomas-siaghi-tellier/cartes
ant compile
```

2. Lancer les tests Blackjack :

```bash
cd ../blackjack
ant junitreport
```

3. Ouvrir le rapport :

```
thomas-siaghi-tellier/blackjack/RapportTest/index.html
```
	
## Rapport des test 
    - Vous trouverez un premier tableaux "Summary" qui résume l'entièreté des test avec le pourcentage de réussite de ceci.
    - En dessous de celui ci vous trouverez les réussites par packages sur lesquelles vous pouvez cliquer afin d'avoir le détail pour chaque classes contenue dans le package.

## Documentation
    
Pour plus de détail sur l'architecture se référer au rapport fourni : 
-- thomas-siaghi-tellier/livraison/rapport/Rapport.pdf
-- thomas-siaghi-tellier/rapport/Rapport.pdf

## Auteurs :
THOMAS Matthieu
TELLIER Basile (https://github.com/Garagorn)
SIAGHI Massinissa (https://github.com/siaghi2250)
