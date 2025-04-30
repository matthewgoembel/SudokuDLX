import java.util.List;

public class SudokuApp {
    public static void main(String[] args) {
        List<SudokuBoard> puzzles = HardcodedPuzzles.getPuzzles();
        List<String> names   = HardcodedPuzzles.getNames();

        for (int i = 0; i < puzzles.size(); i++) {
            SudokuBoard puzzle = puzzles.get(i);
            System.out.println("=== Puzzle " + (i+1) + ": " + names.get(i) + " ===");
            System.out.println(puzzle);

            // Backtracking
            SudokuBackTrack bt = new SudokuBackTrack();
            long t0 = System.nanoTime();
            SudokuBoard solBt = bt.solve(puzzle.copy());
            long t1 = System.nanoTime();
            double dtBt = (t1 - t0) / 1_000_000.0;
            System.out.printf(
              "--- Backtracking: recursive calls = %d, time = %.5f ms%n",
              bt.getCalls(), dtBt
            );
            System.out.println(solBt);

            // DLX
            SudokuDlx dlx = new SudokuDlx();
            t0 = System.nanoTime();
            SudokuBoard solDlx = dlx.solve(puzzle.copy());
            t1 = System.nanoTime();
            double dtDlx = (t1 - t0) / 1_000_000.0;
            System.out.printf(
              "--- DLX: link updates = %d, time = %.5f ms%n",
              dlx.getLinkUpdates(), dtDlx
            );
            System.out.println(solDlx);
        }
    }
}
