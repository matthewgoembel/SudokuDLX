// SudokuSolver.java
public interface SudokuSolver {
    /**
     * Solve the given puzzle and return a completed board.
     * The input board must not be modified.
     */
    SudokuBoard solve(SudokuBoard puzzle);
}
