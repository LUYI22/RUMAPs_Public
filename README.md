# RUMaps

This repository is a Rutgers University data structures assignment starter for a simplified street network. The goal is to model streets, blocks, intersections, and paths using Java data structures and fill in the required logic in `src/rumaps/RUMaps.java`.

This project is intentionally kept as a coursework skeleton: the core assignment methods are provided as stubs, and students are expected to complete the implementations themselves. The codebase is an assignment project built with the JDK and standard Java Swing.

**Quick links**

- Main GUI entry point: `rumaps.Driver`
- Map data used by default: `Coordinates.in`

## Features

- Read street and block geometry from a simple input file
- Build a network model with `Street`, `Block`, `Intersection`, and `Coordinate`
- Compute path choices: minimize intersections (fewest turns) and fastest path (least traffic)
- Simple Swing UI to view the map and interact with route visualizations

## Prerequisites

- Java Development Kit (JDK) 11 or newer installed and available on your `PATH`.
- `zsh` or any POSIX shell (commands below use `zsh` for macOS users).

## Build & Run

From the project root run the following commands to compile and start the GUI:

```bash
# create output directory and compile all sources
mkdir -p bin
javac -d bin $(find src -name '*.java')

# run the GUI (the Driver class is the application entry point)
java -cp bin rumaps.Driver
```

Notes:

- The `Driver` class sets a default input file (`Coordinates.in`) at startup. Put your input file in the project root or change `Driver.networkPath` if you want a different location.
- If you prefer to compile a single edited file while developing, compile only that file for speed, for example:

```bash
javac -d bin src/rumaps/MapPanel.java
```

## Input file format

The example input file `Coordinates.in` included in the repo follows the project's expected format. The program reads:

- Number of streets
- For each street: the number of words in the street name, the words of the name, and the number of blocks
- For each block: block number, number of coordinate points, stroke size, then the x/y pairs for each point

This simple format is parsed by the `RUMaps` initializer (see `src/rumaps/RUMaps.java`).

## Project Structure

- `src/rumaps/` — Java source files
  - `RUMaps.java` — assignment skeleton for the network initialization and path algorithms
  - `Driver.java` — GUI bootstrap (contains `main`) and high-level app wiring
  - `MapPanel.java` — Swing panel that draws the map and highlights paths
  - `Network.java`, `Street.java`, `Block.java`, `Intersection.java`, `Coordinate.java` — core data model
  - `StdIn.java`, `StdOut.java`, `StdRandom.java` — simple I/O / utilities used by the project
- `Coordinates.in` — example map input used for the demo
- `assets/` — image assets used by the UI (satellite overlay, etc.)
- `bin/` — compiled classes (created by `javac -d bin`)

## Usage Tips

- Use the VS Code Java extensions (Java Extension Pack) to open, run, and debug the project easily.
- If you see a `NoClassDefFoundError` or similar at runtime, ensure classes were compiled into `bin` using `-d bin` and that you use `-cp bin` when running.
- To change the random traffic generation reproducibly, adjust the seed in `Driver` (`SEED` constant).

## Contributing

- File an issue for bugs or feature suggestions.
- For code changes, fork the repo, create a branch, implement your change, and submit a pull request. Keep changes focused and include short descriptions of what you changed and why.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

---
