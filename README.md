# SudokuDLX
Light speed sudoku soling algorithm featuring the dancing links technique 

Recources:

[Research:](https://docs.google.com/document/d/1QE-hWEX2Ob7reLcK6cAKCzXfE1c5jjmsXxfhzPc4Jb4/edit?usp=sharing)   -->

[Presentation:](https://docs.google.com/presentation/d/1snFATBnYyF67QqVhr9U4-7NhtG4ReX8nK3pKWvlAyWM/edit?usp=sharing)   -->

# Sudoku Solver Comparison

A command-line Java application and web-based GUI to compare two Sudoku solving algorithms:

- **Recursive Backtracking**
- **Donald Knuth’s Dancing Links (DLX)**

## Features

- Solves hard-coded Sudoku puzzles with both algorithms.
- CLI application (`java SudokuApp`) prints puzzles, solutions, and execution times.
- Web GUI (`index.html`) offers interactive, in-browser comparison with visual grids and performance metrics.

## Project Structure

```
.
├── SudokuBoard.java        # Core model for the 9×9 grid
├── HardcodedPuzzles.java   # Sample puzzles loader
├── SudokuSolver.java       # Solver interface
├── SudokuBackTrack.java    # Recursive backtracking solver
├── SudokuDlx.java          # DLX (exact cover) solver
├── SudokuApp.java          # CLI entry point
└── index.html              # Browser-based GUI
```

## Getting Started

### Prerequisites

- Java 8+ (JDK installed)
- A modern web browser (for the GUI)

### Build & Run CLI

1. Compile all Java classes:
   ```bash
   javac *.java
   ```
2. Run the CLI application:
   ```bash
   java SudokuApp
   ```
3. Observe the console output showing each puzzle, its backtracking solution and time, and its DLX solution and time.

### Use the Web GUI

1. Open `index.html` in your browser (double-click or serve via GitHub Pages).
2. Select a puzzle from the dropdown and click **Load Puzzle**.
3. Click **Solve Both** to display:
   - **Input Puzzle**
   - **Backtracking Solution** with time and call counts
   - **DLX Solution** with time and operation counts

## Code Overview

### SudokuBoard.java

- Represents the Sudoku grid as a 2D `int[9][9]` array.
- Validates placements (`isValidPlacement`).
- Supports deep copy and formatted string output.
- Static helper `fromString(String)` to load puzzles.

### HardcodedPuzzles.java

- Stores puzzles as 81-character strings.
- Provides `getPuzzles()` to retrieve a `List<SudokuBoard>`.

### SudokuSolver.java

- Interface defining `SudokuBoard solve(SudokuBoard puzzle)`.

### SudokuBackTrack.java

- Implements the `SudokuSolver` interface.
- Uses recursive backtracking (DFS) to fill in blanks.

### SudokuDlx.java

- Implements the `SudokuSolver` interface using Knuth’s DLX.
- Models Sudoku as an exact-cover problem (324 constraints, 729 options).
- Uses a 4-way linked structure for fast cover/uncover operations.

### SudokuApp.java

- Loads hard-coded puzzles.
- For each puzzle:
  1. Prints the initial board.
  2. Solves with backtracking and prints solution/time.
  3. Solves with DLX and prints solution/time.

### index.html

- Client-side GUI with JavaScript versions of both solvers.
- Visual 9×9 grids, performance metrics, and puzzle selection.

## License

This project is licensed under the MIT License. See `LICENSE` for details.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

