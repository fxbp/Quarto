# IA - Pràctica Quarto 

> Autor: Francesc Xavier Bullich Parra


Implementació d'un algorimte Minimax per tal que el player (màquina)  pugui jugar al Quarto amb un altre IA.


[Explicació del Quarto]( https://es.wikipedia.org/wiki/Quarto_(juego))

## Entorn Bàsic

Es proporcionen un seguit de classes que permeten la sincronització entre els 2 jugadors i donen una interficie gràfica per poder veure el desenvolupament de les partides facilment. Aquestes clases no seran modificades.

  - ImageRenderer.java
  - Tauler.java
  - UI.form
  - UI.java


També es proporcionen 2 clases jugador (Player1 i Player2) que fan tirades simples. En el meu cas deixarè Player2 sense modificar i implementare el meu juagador nomes a Player1.

## Implementació de l'algoritme

Com que es tracta d'un joc bipersonal l'algoritme que utilitzarem en principi serà el MiniMax.

### Definició dels Nodes

En el joc del Quarto cada jugador ha de realitzar dues accions en el seu torn:

  - Posar la peça que ha rebut al tauler
  - Triar la peça que dona al contrari
	
Així doncs cada node haurà de representar aquestes 2 accions. Els nodes Max representaran el torn del meu jugador i els Min representaran el torn del jugador contrari.
Per tant el que buscarem serà maximitzar el meu moviment en un node Max i minimitzar l'impacte de la peça que donem al contrari en un node Min.

Per represnetar la tirada cada Node haura de guardar tant la posicio del tauler on s'ha posat la peça rebuda com la peça que es dona al ribal.
Per tant a cada nivell tindem tants nodes com posicions buides qudin al tauler. A més com que hem de donar una peça de les restants al contrari, per cada posicio del tauler haurem d'assignar totes les peçes restants (1 peça per cada Node).

### Anàlisi del MiniMax

Un cop definit com és un node mirem com funciona el minimax.

El minimax sense modificacións baixa tots els nivells de nodes i a partir d'all selecciona el cami millor segons l'heuristic del node i si es tracta d'un node min o un node max.

Tenint tot això en compte Mirem quin és l'espai total d'estats.
Partint del cas base, tauler buit i tinc una peça per posar, hi ha 16 possibles caselles on puc tirar i a més per a cada un d'aquestes tinc 15 peces per donar.
El moviment del contrari serà igual pero amb 1 posicio al tauler menys i una peça menys per donar. Seran 15 posicions al tauler i 14 peces per donar.

Si es segueix la progressió obtenim aquest nombre de nodes: 16*15!*15! --> aprox 2.7x10^25.

Aquest número inicial és inasumible per tant s'ha de buscar una alternativa.

#### MiniMax amb nivells

Una solució és no anar fins a baix de tot de l'arbre. El Minimax ja preveu una profunditat màxima a la qual es vol anar per tant farem servir aquesta opció en comptes d'anar fins a l'últim nivell al inici del joc.
D'aquesta manera tenim menys informació i per tant les tirades segurament no seran tant bones com si s'anés fins l'últim nivell, però fent-ho així es pot tenir un nombre raonable de nodes fulla.

També es pot tenir en compte que les 2-3 primeres tirades no son decisives, per tant es podria fer servir un sistema diferent en aquest punt i a partir d'aquí aplicar el minimax. Les primeres tirades són les que generen més nodes, hi ha més espais disponibles i més peces restants.

D'altra banda arribat a cert punt s'ha d'intentar baixar el màxim nivells possibles. A partir de la 3ra tirada s'haurien de començar a tenir en compte les possibles situacions i per tant a baixar més nivells.
El funcionament del minimax però encara no ens serveix del tot per aquest proposit. Si s'intenta baixar molts nivells en un estat incial del joc l'algoritme pot no tardar molt i fins i tot donar-nos un error d'excés de memòria.


#### AlfaBeta

Vistes les limitacions del Minimax, l'algoritme utilitzat es l'AlfaBeta. En aquest cas també es comença amb poca profunditat al inici.

Gràcies a que l'algoritme AlfaBeta anirà podant totes aquelles branques que no milloren el resultat fins ara, es pot incrementar el nombre de nivells al inici del joc.
Aquests nivells inicials estan encara una mica limitats segons la representació que es faci de l'estat. D'altra banda permet baixar fins al final despres d'uns quants moviments.

## Heurístic

L'heurístic utilitzat es basa en una puntuaió de l'estat de tot el tauler a cada fulla. Es tenen en compte sobretot situacions que afaborexen molt al jugador com les que donen moltes possibilitats de guanyar al rival.

### Anàlisi de Situacions

Situacions beneficioses:
	
	- El jugador ha posat la 4 peça que fa Quarto en una columna, fila o diagonal.
	- El jugador ha posat la 2 peça que permetria acabar amb Quarto en una columna, fila o diagonal.
	- El contrari ha posat la 3 peça que permetria acabar amb Quarto en una columna, fila o diagonal.
	
Situacions de desventatge:

	- El contrari ha posat la 4 peça que fa Quarto en una columna, fila o diagonal.
	- El contrari ha posat la 2 peça que permetria acabar amb Quarto en una columna, fila o diagonal.
	- El jugador ha posat la 3 peça que permetria acabar amb Quarto en una columna, fila o diagonal.

Cada una de d'aquestes situacions es puntua mes alt o més baix segons les peces colocades. El fet de posar 4 peces i guanyar es puntua molt alt, per contra el fet de posar 4 peces i perdre es puntua molt baix.
A més les situacions de 3 peces que podrien donar-li la victoria al rival es puntuen molt pitjor que el cas contrari. 
Per tant les jugades que farien perdre al jugador tenen mes valor (negatiu) que les que el poden fer guanyar. Per tant el que s'intenta es que el rival no guanyi (a part del propi algoritme que ja busca aquest fi).

D'altra banda també es te en compte el nombre de propietats que es mantenten en files, columnes i diagonals. De forma que una jugada que permeti acabar un quarto de diferents formes tindra una puntuació molt mes alta que una que només permeti fer quarto amb 1 propietat.

### Representació de l'estat

Per poder puntuar d'aquesta manera es proposa la seguent representació de l'estat.

Abans hem parlat de Nodes. Aquest seran els que guarden la posicio del tauler on es fa la tirada del jugador i quina peça se li dona el contrari, peró falta representar l'estat de tauler en aquell node.

Per fer-ho s'utilitza la Classe Board. El que fa Board es guardar com ha quedat el tauler despres de realitzar una tirada (posicionar la peça que ha rebut el jugador on ell digui).
Pero no es guarda quina peça hi ha a cada posicio del tauler 4x4 sino que es guarda en un tauler de "propietats".

El Board es representa amb: 

	- Una matriu de 4x6 per les files (cada fila representa una de les 4 files)
	- Una matriu de 4x6 per les columnes (cada fila representa una de les 4 columnes)
	- Un array de 6 elements per la diagonal
	- Un array de 6 posicions per la contra-diagonal.
	
Totes les arrays de 6 posicions tenen la mateixa informació:	
	
	- La primera posicio indica quantes peces s'han colocat en aquella fila/columna/diagonal.
	- La segona posicio indica la propietat color de les peces.
	- La tercera posicio indica la propietat forma de les peces.
	- La quarta posicio indica la porpietat forat de les peces.
	- La cinquena posicio indica la propietat mida de les peces.
	- La sisena posicio indica l'ultim jugador que ha posat una peça en aquella fila/columna/diagonal.
	
El que es fa en cada posicio de propietat es anar sumant 1 o 0 segons s'hagin anat colocant les peces.
Amb el numero de peces podem saber si una propietat es cumpleix per totes les peces posades. Per ex si a la fila 1 s'han posat 3 negres, a la columna que indica el color hi haurá un 3, que coincideix amb el  numero de peces posades.
si pel contrari, s'han posat 2 negres i 1 blanca, a la columna color hi haurà un 2, que és diferent al nombre de peces, per tant en aquella fila ja no es compleix la propietat color.
Es poden controlar els 0 de la mateixa manera, si hi ha 3 peces i hi ha un 1 o un 2, sabem que la propietat blanc  que es 0 per totes les peces ja no es compleix.

Exemple d'estat.

Coloquem les peces [Blanc,Rodona,Forat,Gran], [Blanc, Quadrat,Forat,Petit] a la fila 1. (les columnes relacionades i possibles diagonals es veurien afectades tambe)

	Coloquem la primera
	
	Fila[0]= [1,0,0,1,1,1]
	
	Coloquem la segona
	
	Fila[0]= [2,0,1,2,1,2]
	
En aquest exemple tenim que les propietats Color(blanc =0) i Forat(amb forat =1) es segueixen cumplint per les 2 peces, les altres propietats ja no es compleixen per tant ja no es possible fer quarto per elles.
