// SudokuApp.java
 import java.util.List;
 
 public class SudokuApp {
     public static void main(String[] args) {
         List<SudokuBoard> puzzles = HardcodedPuzzles.getPuzzles();
 
         for (int i = 0; i < puzzles.size(); i++) {
             SudokuBoard puzzle = puzzles.get(i);
             System.out.println("=== Puzzle " + (i+1) + " ===");
             System.out.println(puzzle);
 
             // Backtracking
             SudokuSolver btSolver = new SudokuBackTrack();
             long t0 = System.currentTimeMillis();
             SudokuBoard btSolution = btSolver.solve(puzzle.copy());
             long dt = System.currentTimeMillis() - t0;
             System.out.println("--- Backtracking (ms): " + dt);
             System.out.println(btSolution);
 
             // DLX
             SudokuSolver dlxSolver = new SudokuDlx();
             t0 = System.currentTimeMillis();
             SudokuBoard dlxSolution = dlxSolver.solve(puzzle.copy());
             long dt2 = System.currentTimeMillis() - t0;
             System.out.println("--- DLX (ms): " + dt2);
             System.out.println(dlxSolution);
         }
     }
 }
