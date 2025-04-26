// SudokuBackTrack.java
public class SudokuBackTrack implements SudokuSolver {

    @Override
    public SudokuBoard solve(SudokuBoard puzzle) {
        // work on a copy so we don't mutate the original
        SudokuBoard board = puzzle.copy();
        solveRec(board);
        return board;
    }

    private boolean solveRec(SudokuBoard board) {
        for (int r = 0; r < SudokuBoard.SIZE; r++) {
            for (int c = 0; c < SudokuBoard.SIZE; c++) {
                if (board.get(r, c) == 0) {
                    for (int val = 1; val <= 9; val++) {
                        if (board.isValidPlacement(r, c, val)) {
                            board.set(r, c, val);
                            if (solveRec(board)) {
                                return true;
                            }
                            board.set(r, c, 0);
                        }
                    }
                    // no valid number -> backtrack
                    return false;
                }
            }
        }
        // no empty cell left -> solved
        return true;
    }
}
