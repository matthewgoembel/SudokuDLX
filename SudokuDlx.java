import java.util.*;

public class SudokuDlx implements SudokuSolver {

    private long linkUpdates;  // count of pointer/size updates

    private class Node {
        Node left, right, up, down;
        ColumnNode column;
        int rowIndex, colIndex, num;
    }

    private class ColumnNode extends Node {
        int size = 0;
        String name;
        ColumnNode(String name) {
            this.name = name;
            this.column = this;
            left = right = up = down = this;
        }
    }

    private ColumnNode header;
    private Node[] solution;

    @Override
    public SudokuBoard solve(SudokuBoard puzzle) {
        linkUpdates = 0;
        int[][] board = new int[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                board[r][c] = puzzle.get(r, c);

        header = buildMatrix(board);
        solution = new Node[81];
        int[][] result = hardCopy(board);
        search(0, result);
        return new SudokuBoard(result);
    }

    /** Returns total number of pointer/size updates performed. */
    public long getLinkUpdates() {
        return linkUpdates;
    }

    private ColumnNode buildMatrix(int[][] board) {
        ColumnNode root = new ColumnNode("root");
        ColumnNode[] cols = new ColumnNode[324];
        ColumnNode prev = root;
        // create headers
        for (int i = 0; i < 324; i++) {
            ColumnNode col = new ColumnNode("C" + i);
            cols[i] = col;
            col.left = prev; linkUpdates++;
            col.right = root; linkUpdates++;
            prev.right = col; linkUpdates++;
            root.left = col; linkUpdates++;
            prev = col;
        }
        // add rows
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = board[r][c];
                if (v != 0) addRow(cols, r, c, v);
                else for (int n = 1; n <= 9; n++) addRow(cols, r, c, n);
            }
        }
        return root;
    }

    private void addRow(ColumnNode[] cols, int r, int c, int n) {
        int[] idx = new int[]{
            r*9 + c,
            81  + r*9 + (n - 1),
            162 + c*9 + (n - 1),
            243 + ((r/3)*3 + (c/3))*9 + (n - 1)
        };
        Node first = null;
        for (int ci : idx) {
            ColumnNode col = cols[ci];
            Node node = new Node();
            node.column = col;
            node.rowIndex = r;
            node.colIndex = c;
            node.num = n;
            // vertical link: node between col.up and col
            node.down = col; linkUpdates++;
            node.up   = col.up; linkUpdates++;
            col.up.down = node; linkUpdates++;
            col.up = node; linkUpdates++;
            col.size--;  // no, actually this is an insertion, so increment
            col.size++; linkUpdates++;
            // horizontal link
            if (first == null) {
                first = node;
                node.left = node; linkUpdates++;
                node.right = node; linkUpdates++;
            } else {
                node.right = first; linkUpdates++;
                node.left  = first.left; linkUpdates++;
                first.left.right = node; linkUpdates++;
                first.left = node; linkUpdates++;
            }
        }
    }

    private boolean search(int k, int[][] board) {
        if (header.right == header) return true;
        ColumnNode col = selectColumn();
        cover(col);
        for (Node row = col.down; row != col; row = row.down) {
            solution[k] = row;
            board[row.rowIndex][row.colIndex] = row.num;
            for (Node j = row.right; j != row; j = j.right) {
                cover(j.column);
            }
            if (search(k + 1, board)) return true;
            for (Node j = row.left; j != row; j = j.left) {
                uncover(j.column);
            }
            board[row.rowIndex][row.colIndex] = 0;
        }
        uncover(col);
        return false;
    }

    private ColumnNode selectColumn() {
        int min = Integer.MAX_VALUE;
        ColumnNode best = null;
        for (ColumnNode c = (ColumnNode) header.right; c != header; c = (ColumnNode) c.right) {
            if (c.size < min) {
                min = c.size; best = c;
            }
        }
        return best;
    }

    private void cover(ColumnNode col) {
        // remove header
        col.right.left = col.left; linkUpdates++;
        col.left.right = col.right; linkUpdates++;
        // remove rows
        for (Node r = col.down; r != col; r = r.down) {
            for (Node j = r.right; j != r; j = j.right) {
                j.down.up = j.up; linkUpdates++;
                j.up.down = j.down; linkUpdates++;
                j.column.size--; linkUpdates++;
            }
        }
    }

    private void uncover(ColumnNode col) {
        for (Node r = col.up; r != col; r = r.up) {
            for (Node j = r.left; j != r; j = j.left) {
                j.column.size++; linkUpdates++;
                j.down.up = j; linkUpdates++;
                j.up.down = j; linkUpdates++;
            }
        }
        col.right.left = col; linkUpdates++;
        col.left.right = col; linkUpdates++;
    }

    private int[][] hardCopy(int[][] orig) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(orig[i], 0, copy[i], 0, 9);
        }
        return copy;
    }
}
