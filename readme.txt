GAME-SPECIFIC REQUIREMENTS:
The game is implemented using the Complete Rules. At the beginning of the game, players can choose between a level 0 match, which corresponds to the test flight, and a level 1 match, in which they can select either a level 1 or level 2 shipboard.

GAME-AGNOSTIC REQUIREMENTS:
The server supports both TCP and RMI connections, and the client can choose which one to use. The game provides both a TUI (Text User Interface) and a GUI (Graphical User Interface); the client can choose which interface to use when connecting.

ADVANCED FEATURES:
- Test Flight: select Game Mode 0 if you want to play a test flight  
- Persistence: the server periodically saves the game state in the designated folder and create a file with the current data and time
    To reload a game saved you need to text: java "-Dfile.encoding=UTF-8" -jar GalaxyTruckerServer-jar-with-dependencies.jar <nameFile>

HOW TO START SERVER AND CLIENT:
Server:
java "-Dfile.encoding=UTF-8" -jar GalaxyTruckerServer-jar-with-dependencies.jar

Client TCP TUI:
first command: chcp 65001 (to display colors)  
second command: java "-Dfile.encoding=UTF-8" -jar GalaxyTruckerClient-jar-with-dependencies.jar tcp tui <ServerIp> 9191

Client TCP GUI:
java "-Dfile.encoding=UTF-8" -jar GalaxyTruckerClient-jar-with-dependencies.jar tcp gui <ServerIp> 9191

Client RMI TUI:
first command: chcp 65001 (to display colors)  
second command: java "-Dfile.encoding=UTF-8" -jar GalaxyTruckerClient-jar-with-dependencies.jar rmi tui <ServerIp> 3600

Client RMI GUI:
java "-Dfile.encoding=UTF-8" -jar GalaxyTruckerClient-jar-with-dependencies.jar rmi gui <ServerIp> 3600

