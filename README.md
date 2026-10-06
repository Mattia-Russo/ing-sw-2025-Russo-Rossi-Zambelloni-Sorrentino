# Galaxy Trucker

This project is a Java-based implementation of the board game Galaxy Trucker, developed as a client/server application with a networked multiplayer flow.

The repository includes the game logic and packaged deliverables, including client and server JAR files. The generated Javadoc is present but still incomplete and mostly empty, as the documentation work has not been finalized yet.

## Project status

This project is currently under active development. Some bugs and edge cases are still being resolved, and the codebase is not yet considered fully stable for final release.

**Note:** The JAR files in `Deliberables/` are an older version from before the active bug-fixing process began. They may not reflect the current state of the project.

## Overview

The application is structured around a multiplayer game architecture, with:

- a server-side game controller and state management
- client-side interaction and network communication
- message passing between clients and server
- game logic for ship building, movement, combat, rewards, and end-of-game flow

The repository also contains generated documentation under `Deliberables/Javadoc`, although this documentation is still incomplete and mostly empty because the Javadoc work has not been completed yet.

## Repository structure

- `Deliberables/` — built artifacts and generated documentation (JAR files are outdated)
  - `GalaxyTruckerClient-jar-with-dependencies.jar` — older version
  - `GalaxyTruckerServer-jar-with-dependencies.jar` — older version
  - `Javadoc/` — generated JavaDoc for the project (still unfinished / largely empty)
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

The codebase is functional in its current form, but it still contains issues that are being actively addressed. Some gameplay edge cases, synchronization issues, or logic inconsistencies may still require fixes before the project is considered complete. At the same time, the Javadocs are still unfinished and should be considered mostly empty for the time being.

The JAR files provided in `Deliberables/` are from an earlier snapshot of the project and do not represent the current work-in-progress state. They should be rebuilt if you want to test the latest version of the code.

## Running the project

The repository includes packaged executable JAR files under `Deliberables/`, however these are outdated:

```bash
java -jar Deliberables/GalaxyTruckerServer-jar-with-dependencies.jar
java -jar Deliberables/GalaxyTruckerClient-jar-with-dependencies.jar
```

**Note:** For the latest version with bug fixes, you will need to rebuild the JAR files from the current source code.

The exact startup procedure may depend on the environment and network configuration used for the game.

## Documentation

A generated JavaDoc set is available in:

```text
Deliberables/Javadoc/Galaxy.Trucker/
```

This documentation is still a work in progress and should be considered largely incomplete at the moment.

## Contributing

This project is being developed collaboratively, and fixes are still being applied as issues are discovered. Contributions, testing, and bug reports are encouraged.

## License

No explicit license file was found in the repository metadata. If the project is to be distributed or published, it is recommended to add an appropriate license file such as MIT or GPL.
