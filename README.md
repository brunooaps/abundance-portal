# Abundance Portal

A Minecraft Fabric mod based on the universe of Bruno's book. This is the first mod of a planned modpack.

## Development environment

- Minecraft: 26.2
- Fabric Loader: 0.19.3
- Fabric API: 0.154.0+26.2
- Loom: 1.17-SNAPSHOT
- Java: 25+

## Setup

1. Install a JDK (17+, ideally 25) and import this project into Eclipse: `File > Import... > Gradle > Existing Gradle Project`.
2. Run `./gradlew genSources` (Gradle task) once, so the IDE can attach readable Minecraft sources.
3. Run the `runClient` Gradle task to launch the game in the dev environment.

## License

This project's template is based on the official Fabric example mod (CC0). See `LICENSE`.
