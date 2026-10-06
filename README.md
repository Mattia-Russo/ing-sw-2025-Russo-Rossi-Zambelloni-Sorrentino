# Galaxy Trucker

This project is a Java-based implementation of the board game Galaxy Trucker, developed as a client/server application with a networked multiplayer flow.

The repository includes both the game logic and generated deliverables, including packaged client and server JAR files together with Javadoc documentation.

## Project status

This project is currently under active development. Some bugs and edge cases are still being resolved, and the codebase is not yet considered fully stable for final release.

## Overview

The application is structured around a multiplayer game architecture, with:

- a server-side game controller and state management
- client-side interaction and network communication
- message passing between clients and server
- game logic for ship building, movement, combat, rewards, and end-of-game flow

The repository also contains generated documentation under `Deliberables/Javadoc`, which provides an API overview of the main packages and classes.

## Repository structure

- `Deliberables/` — built artifacts and generated documentation
  - `GalaxyTruckerClient-jar-with-dependencies.jar`
  - `GalaxyTruckerServer-jar-with-dependencies.jar`
  - `Javadoc/` — generated JavaDoc for the project
- `.idea/` — IDE configuration files
- `.gitignore` — repository ignore rules

## Main features

- Java implementation of the game logic
- Client/server communication model
- Turn-based game flow
- Network messaging through dedicated message classes
- State-based controller logic for game progression
- Built deliverables for execution and documentation

## Notes about the current state

The codebase is functional in its current form, but it still contains issues that are being actively addressed. Some gameplay edge cases, synchronization issues, or logic inconsistencies may still require fixes before the project is considered complete.

## Running the project

The repository includes packaged executable JAR files under `Deliberables/`:

```bash
java -jar Deliberables/GalaxyTruckerServer-jar-with-dependencies.jar
java -jar Deliberables/GalaxyTruckerClient-jar-with-dependencies.jar
```

The exact startup procedure may depend on the environment and network configuration used for the game.

## Documentation

A generated JavaDoc set is available in:

```text
Deliberables/Javadoc/Galaxy.Trucker/
```

This documentation describes the main packages, classes, and interfaces for both client and server components.

## Contributing

This project is being developed collaboratively, and fixes are still being applied as issues are discovered. Contributions, testing, and bug reports are encouraged.

## License

No explicit license file was found in the repository metadata. If the project is to be distributed or published, it is recommended to add an appropriate license file such as MIT or GPL.
