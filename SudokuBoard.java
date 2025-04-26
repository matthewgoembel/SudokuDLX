import java.util.Arrays;

public class SudokuBoard {
    public static final int SIZE = 9;
    private final int[][] grid;

    public SudokuBoard() {
        grid = new int[SIZE][SIZE];
    }

    public SudokuBoard(int[][] initial) {
        grid = new int[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            System.arraycopy(initial[r], 0, grid[r], 0, SIZE);
        }
    }

    public int get(int row, int col) {
        return grid[row][col];
    }

    public void set(int row, int col, int value) {
        grid[row][col] = value;
    }

    /** 
     * Returns true if placing 'value' at (row, col) does not conflict 
     * with existing numbers in its row, column, or 3×3 block.
     */
    public boolean isValidPlacement(int row, int col, int value) {
        // check row and column
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i] == value) return false;
            if (grid[i][col] == value) return false;
        }
        // check 3×3 block
        int br = (row / 3) * 3;
        int bc = (col / 3) * 3;
        for (int r = br; r < br + 3; r++) {
            for (int c = bc; c < bc + 3; c++) {
                if (grid[r][c] == value) return false;
            }
        }
        return true;
    }

    /** Deep copy of this board */
    public SudokuBoard copy() {
        return new SudokuBoard(this.grid);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < SIZE; r++) {
            if (r % 3 == 0 && r != 0) {
                sb.append("------+-------+------\n");
            }
            for (int c = 0; c < SIZE; c++) {
                if (c % 3 == 0 && c != 0) sb.append("| ");
                int v = grid[r][c];
                sb.append(v == 0 ? ". " : (v + " "));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /** Utility: load from a flat string of 81 chars (digits or '.') */
    public static SudokuBoard fromString(String s) {
        if (s.length() != SIZE * SIZE)
            throw new IllegalArgumentException("Expected 81-char string");
        SudokuBoard b = new SudokuBoard();
        for (int i = 0; i < SIZE * SIZE; i++) {
            char ch = s.charAt(i);
            int r = i / SIZE, c = i % SIZE;
            if (ch >= '1' && ch <= '9') {
                b.set(r, c, ch - '0');
            } else {
                b.set(r, c, 0);
            }
        }
        return b;
    }
}
