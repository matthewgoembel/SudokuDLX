public class SudokuBackTrack implements SudokuSolver {

    private long calls;  // count of recursive calls

    @Override
    public SudokuBoard solve(SudokuBoard puzzle) {
        calls = 0;
        SudokuBoard board = puzzle.copy();
        solveRec(board);
        return board;
    }

    /** Number of times solveRec() was invoked. */
    public long getCalls() {
        return calls;
    }

    private boolean solveRec(SudokuBoard board) {
        calls++;
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
                    return false; // dead end
                }
            }
        }
        return true; // solved
    }
}
