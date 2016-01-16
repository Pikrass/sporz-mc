								Sporz MC manual
				   =========================================

This server mod turns Minecraft into a platform to play Sporz online. Sporz is a
role playing game based on the same principles as Mafia (or Werewolf), but more
complex and interesting. The rules are only available in French at the moment:
    http://www.sporz.fr/wikini/wakka.php?wiki=ReglesSporz

This mod should be used in conjonction with Mumble Link so that players can hear
each other with audio positioning.

We recommend the following setup:
- a Minecraft server with this mod installed (clients don't need the mod);
- a Mumble server all players can join;
- Mumble Link installed on players' machines, configured in such a way that
players can't hear each other past a relatively short distance (e.g. 5 meters);
- a map with locations where the players can talk in small groups, but not
hidden so other players can know who is talking with who by just looking around;
- isolated rooms, with no exit: one for each player, one for mutants and one for
doctors.


Sporz MC includes Sporz's classic rules, without the infection feature (which is
not often used in real life), for their temporary action mutants can only choose
to paralyse someone. All players can choose not to use their action (including
mutants and doctors). No "extra" role is implemented at the moment.


The game makes heavy use of the chatroom. All commands begin with /sporz, then a
subcommand name and its parameters.

At any moment anyone can see the available commands by using:
    /sporz




Configuration
-------------

A configuration file, named "Sporz MC.cfg" and placed in the config directory,
allows you to configure the mod. An example is available in this repository.

The hub, where people are teleported at the beginning of the day, and the
mutants' and doctors' rooms, are specified as a cubic area. Players are
teleported to a random point inside it.

Individual rooms are specified as a single point in space, where the
corresponding player will be teleported.




Starting the game
-----------------

An op has to specify the players and rules for the game.

All currently connected players can be added to the players list, with the
exception of the op themself, with the following command:
    /sporz players init

Players can also be individually added or removed:
    /sporz players add <name>
    /sporz players remove <name>

Rules consist of a number of variables, listed with:
    /sporz rules

These variables include the number of resistant players, the number of host
players, and the number of players for each role (including initial mutants);

It is possible to initialize the rules to their default (varying accordingly to
the number of players currently registers):
    /sporz rules init

It is also possible to set variables individually:
    /sporz rules set <variable> <number>

Initial mutants are always hosts and do not count in the number of host players
configured with this mechanism. In the same way, if there is only one doctor,
he'll be resistant and not counted in the number of resistant players (this is
called the Lyon variant). If there is more than one doctor, they'll be
standards.


Once players and rules are configured, the operator can start the game with:
    /sporz start




Déroulement du jeu
------------------

Events are displayed in the chatroom. When a player has to perform an action,
the game tells them so and the corresponding commands become available. They can
be listed with:
    /sporz

When a player performs an action, their choice is actually saved until all
players chose their own action for this phase. At this moment all actions are
executed and results are displayed.

Before the end of the phase, a player can change their choice as many times they
desire. They can also cancel it to prevent the phase from ending and take more
time to decide with:
    /sporz clear


For some phases, it is possible to decide not to do anything, or to cast a null
vote, with the following command:
    /sporz done

In the case of a vote, this means the player won't take part at all in the
computation of the result.

This command can also be used to realize a "partial" action. For examples if
mutants wish to mutate someone but not to paralyse anybody, they have to use the
mutate command and then the done command. This mechanism works similarly for
doctors.

Players with information roles can also choose not to do anything by typing
"none" instead of a username as the parameter for their command.


During the day, players can cast a "blank" vote. If blank votes get the
majority, nobody is killed. This is done by passing "none" to the command.


During the night, players are teleported in a closed room (attributed randomly
from the room pool, but each player keeps their room throughout the game). When
the mutants have to play, they're teleported together in the mutants' room so
they can speak, then they're teleported back to their individual rooms. The same
thing happens for doctors. When the night ends, everybody is teleported to the
hub.


When a player dies, he's switched to spectator mode so he can listen to
conversations.


When the game is over, it is announced in the chatroom along with the identities
of all players. The operator can also trigger the end of the game with the end
command.
