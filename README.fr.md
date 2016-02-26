Guide d'utilisation du mod Sporz MC
===================================

Ce mod serveur permet d'utiliser Minecraft comme support pour jouer à Sporz via
Internet. Sporz est un jeu de rôle inspiré du Village de Thiercelieux, mais bien
plus complexe et intéressant. Vous pouvez en lire les règles ici :
    http://www.sporz.fr/wikini/wakka.php?wiki=ReglesSporz

L'idée est de l'utiliser en conjonction avec Mumble Link afin de profiter du
positionnement audio.

Nous vous conseillons le set-up suivant :
- un serveur Minecraft doté de ce mod (les clients n'en ont pas besoin) ;
- Mumble avec un accès pour tous les joueurs ;
- Mumble Link installé chez tous les joueurs, et configuré de telle sorte que
les joueurs ne puissent plus s'entendre au-delà d'une distance assez courte (par
exemple 5 mètres) ;
- une map permettant de s'isoler, mais où les autres joueurs peuvent savoir qui
parle avec qui en se baladant un peu ;
- des salles closes, sans issue : une par joueur, ainsi qu'une pour les mutants
et une pour les médecins.


Sporz MC inclue les règles classiques de Sporz, décrites sur le lien plus haut,
à l'exception de l'infection (qui n'est presque plus utilisée en pratique). Les
mutants ne peuvent donc que paralyser pour leur action à effet temporaire. Tout
joueur peut décider de ne pas agir pour son action (y compris mutants et
médecins). Aucun rôle "bonus" n'est implémenté pour l'heure.


Le jeu est piloté par l'intermédiaire de commandes dans le chat. Toutes les
commandes débutent par /sporz, suivi du nom de la commande et de ses éventuels
paramètres.

Il est à tout moment possible de connaître les commandes utilisables via :
    /sporz




Configuration
-------------

Un fichier de configuration nommé "Sporz MC.cfg" et placé dans le répertoire
"config" du serveur permet de positionner quelques options. Un exemple vous est
fourni dans ce dépôt : à vous de le modifier à votre convenance.

Le hub, où les joueurs sont téléportés au début du jour, ainsi que les salles
des médecins et des mutants, sont spécifiées par une zone cubique. Les joueurs
seront téléportés en un point de la zone au hasard.

Les salles sont représentées par un simple point sur lequel le joueur sera
téléporté.

Le jeu est disponible en anglais (en) ou en français (fr).




Préparation de la partie
------------------------

Un opérateur du serveur doit renseigner les joueurs ainsi que les règles.

Il est possible d'ajouter tous les joueurs actuellement connectés, à l'exception
de l'opérateur, avec la commande :
    /sporz players init

Les joueurs peuvent aussi être individuellement ajoutés et retirés avec :
    /sporz players add <nom>
    /sporz players remove <nom>

Les règles consistent en un ensemble de variables consultables via :
    /sporz rules

Ces variables comprennent le nombre de joueurs résistants, le nombre de joueurs
hôtes, et le nombre de joueurs pour chaque rôle, en incluant les mutants
initiaux.

Il est possible d'initialiser les règles par défaut en fonction du nombre de
joueurs actuellement enregistrés via la commande :
    /sporz rules init

Il est aussi possible de modifier chaque variable individuellement via :
    /sporz rules set <variable> <nombre>

Les mutants initiaux sont forcément hôtes et ne comptent pas dans le nombre de
joueurs hôtes configuré ainsi. De la même manière, s'il n'y a qu'un seul
médecin, il est résistant (on appelle cela la "variante lyonnaise") et ne compte
pas dans le nombre de résistants. S'il y a plus d'un médecin, ils sont forcément
standards.


Une fois les joueurs et les règles configurés, l'opérateur lance la partie :
    /sporz start




Déroulement du jeu
------------------

Les événements sont affichés dans le chat. Quand un joueur doit réaliser une
action, il en est prévenu et les commandes correspondantes deviennent
disponibles. Elles sont consultables à l'aide de
    /sporz

Lorsqu'un joueur réalise une action, son choix est enregistré jusqu'à ce que
tous les autres joueurs aient réalisé leur action pour cette phase. A ce moment
les actions sont effectuées et les résultats sont dévoilés. Un timer aléatoire
impose aussi une durée minimale pour certaines phases, afin que les joueurs ne
puissent pas (trop) déduire d'informations par ce biais.

Avant la fin de la phase, un joueur peut modifier son choix autant qu'il le
souhaite. Il peut aussi l'annuler temporairement afin de prendre le temps de la
réflexion, via
    /sporz clear


Pour certaines phases, il est possible de décider de ne rien faire, ou de voter
nul, avec la commande :
    /sporz done

Dans le cas d'un vote, cela signifie que vous ne compterez pas pour le calcul
du résultat (vote nul).

Cette commande peut aussi être utilisée pour réaliser une action partielle. Par
exemple si les mutants décident de muter quelqu'un mais de ne pas paralyser, ils
doivent effectuer l'action pour muter puis lancer /sporz done. Cela est valable
aussi pour les soins des médecins.

Pour choisir de ne rien faire dans le cas d'un rôle à information, écrivez
"none" au lieu d'un nom de joueur.


Lors du vote pour le meurtre en fin de journée, il est possible de voter blanc.
Si les votes blancs sont majoritaires, personne n'est tué. Cela est réalisé en
passant l'argument "none" à la commande correspondante.


La nuit, les joueurs sont téléportés dans une salle close (attribuée au hasard,
mais la même pour toute la partie). Lors du tour des mutants, les mutants sont
téléportés dans une salle ensemble pour discuter, puis retournent à leur salle
individuelle. La même chose se produit pour le tour des médecins. Lorsque le
jour se lève, tout le monde est téléporté au hub.


Quand un joueur meurt, il est passé en mode spectateur pour qu'il puisse
écouter les conversations.


Quand la partie est terminée, cela est annoncé sur le chat et les identités de
chaque joueur sont dévoilées. L'opérateur peut aussi déclencher la fin de la
partie avec la commande "end".
