import java.util.List;

public class SudokuApp {
    public static void main(String[] args) {
        List<SudokuBoard> puzzles = HardcodedPuzzles.getPuzzles();

        for (int i = 0; i < puzzles.size(); i++) {
            SudokuBoard puzzle = puzzles.get(i);
            System.out.println("=== Puzzle " + (i + 1) + ": " + HardcodedPuzzles.getNames().get(i) + " ===");
            System.out.println(puzzle);

            // Backtracking
            SudokuBackTrack btSolver = new SudokuBackTrack();
            long t0 = System.nanoTime();
            SudokuBoard btSolution = btSolver.solve(puzzle.copy());
            long t1 = System.nanoTime();
            double dtMs = (t1 - t0) / 1_000_000.0;
            System.out.printf(
              "--- Backtracking: Steps: %d, Time: %.5f ms%n",
              btSolver.getCalls(), dtMs
            );
            System.out.println(btSolution);

            // DLX
            SudokuDlx dlxSolver = new SudokuDlx();
            t0 = System.nanoTime();
            SudokuBoard dlxSolution = dlxSolver.solve(puzzle.copy());
            t1 = System.nanoTime();
            double dt2Ms = (t1 - t0) / 1_000_000.0;
            System.out.printf(
              "--- DLX: Steps: %d, Time: %.5f ms%n",
              dlxSolver.getSteps(), dt2Ms
            );
            System.out.println(dlxSolution);
        }
    }
}
