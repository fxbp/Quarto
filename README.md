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

Una solució és no anar fins a baix de tot de l'arbre. Per fer-ho s'estableix un nivell al del qual no es baixarà i es calcularà l'heuristic allà.
D'aquesta manera tenim menys informació i per tant les tirades segurament no seran tant bones com si s'anés fins l'últim nivell, però fent-ho així es pot tenir un nombre raonable de nodes fulla.

També es pot tenir en compte que les 2-3 primeres tirades no son decisives, per tant es podria fer servir un sistema diferent en aquest punt i a partir d'aquí aplicar el minimax. Les primeres tirades són les que generen més nodes, hi ha més espais disponibles i més peces restants.

D'altra banda arribat a cert punt s'ha d'intentar baixar el màxim nivells possibles. A partir de la 3ra tirada s'haurien de començar a tenir en compte les possibles situacions i per tant a baixar més nivells.
El funcionament del minimax però encara no ens serveix del tot per aquest proposit. Si s'intenta baixar molts nivells en un estat incial del joc l'algoritme pot no tardar molt i fins i tot donar-nos un error d'excés de memòria.


#### AlfaBeta amb nivells

Vistes les limitacions del Minimax, l'algoritme utilitzat es l'AlfaBeta amb nivells (un AlfaBeta normal també te la limitació d'un nombre massa gran de nodes al inici).

Gràcies a que l'algoritme AlfaBeta anirà podant totes aquelles branques que no milloren el resultat fins ara, es pot incrementar el nombre de nivells al inici del joc.
Aquests nivells inicials estan encara una mica limitats segons la representació que es faci de l'estat. D'altra banda permet baixar fins al final despres d'uns quants moviments.

## Heurístic

