// SudokuApp.java
import java.util.List;

public class SudokuApp {
    public static void main(String[] args) {
        List<SudokuBoard> puzzles = HardcodedPuzzles.getPuzzles();

        for (int i = 0; i < puzzles.size(); i++) {
            SudokuBoard puzzle = puzzles.get(i);
            System.out.println("=== Puzzle " + (i + 1) + " ===");
            System.out.println(puzzle);

            // Backtracking
            SudokuSolver btSolver = new SudokuBackTrack();
            long t0 = System.nanoTime();  // ⬅️ use nanoTime for more precision
            SudokuBoard btSolution = btSolver.solve(puzzle.copy());
            long t1 = System.nanoTime();
            double dtMs = (t1 - t0) / 1_000_000.0;  // ⬅️ convert nanos -> millis
            System.out.printf("--- Backtracking (ms): %.5f\n", dtMs);  // ⬅️ 5 decimals
            System.out.println(btSolution);

            // DLX
            SudokuSolver dlxSolver = new SudokuDlx();
            t0 = System.nanoTime();
            SudokuBoard dlxSolution = dlxSolver.solve(puzzle.copy());
            t1 = System.nanoTime();
            double dt2Ms = (t1 - t0) / 1_000_000.0;
            System.out.printf("--- DLX (ms): %.5f\n", dt2Ms);  // ⬅️ 5 decimals
            System.out.println(dlxSolution);
        }
    }
}
