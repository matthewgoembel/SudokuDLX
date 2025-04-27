import java.util.*;

// ===============================
// Sudoku DLX Search Algorithm
// ===============================

 public class SudokuDlx implements SudokuSolver {

    private long STEPS;  // Count total # of recursive steps

    // Element in the dancing links matrix
    private class Node {
        Node left, right, up, down;
        ColumnNode column;
        int rowIndex, colIndex, num;
    }

    // Node for a column header for each constraint
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

    // Starts the solve of the board using dlx algorithm 
    @Override
    public SudokuBoard solve(SudokuBoard puzzle) {
        int size = SudokuBoard.SIZE;
        int[][] board = new int[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                board[r][c] = puzzle.get(r, c);
            }
        }

        STEPS = 0;
        header = buildMatrix(board);
        solution = new Node[81];
        int[][] result = hardCopy(board);
        search(0, result);
        
        return new SudokuBoard(result);
    }

    // Build the cover matrix for the board
    private ColumnNode buildMatrix(int[][] board) {
        int COLS = 324;  // Total # of constraints
        ColumnNode root = new ColumnNode("root");
        ColumnNode[] columns = new ColumnNode[COLS];

        ColumnNode prev = root;
        for (int i = 0; i < COLS; i++) {
            ColumnNode col = new ColumnNode("C" + i);
            columns[i] = col;
            col.left = prev;
            col.right = root;
            prev.right = col;
            root.left = col;
            prev = col;
        }

        for (int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                int val = board[r][c];

                if (val != 0) {
                    addRow(columns, r, c, val);
                } else {
                    for (int n = 1; n <= 9; n++) {
                        addRow(columns, r, c, n);
                    }
                }   

            }
        }
        return root;
    }
    
    // Create rows for each node in the board matrix
    private void addRow(ColumnNode[] cols, int r, int c, int n) {
        int[] colIndices = new int[]{
            r * 9 + c,
            81 + r * 9 + (n - 1),
            162 + c * 9 + (n - 1),
            243 + ((r / 3) * 3 + (c / 3)) * 9 + (n - 1)
        } 

        Node first = null;
        for (int idx : colIndices) {
            ColumnNode col = cols[idx];
            Node node = new Node();
            node.column = col;
            node.rowIndex = r;
            node.colIndex = c;
            node.num = n;

            node.down = col;
            node.up = col.up;
            col.up.down = node;
            col.up = node;
            col.size++;
        }

        if (first == null) {
            first = node;
            node.left = node.right = node;
        } else {
            node.left = first.left;
            node.right = first;
            first.left.right = node;
            first.left = node;
        }
    }

    // Validate the current search path of moves 
    private boolean search(int k, int[][] board) {
        if (header.right == header) return true;

        ColumnNode col = selectColumn();
        if (col.size == 0) return false;

        cover(col);
        for (Node row = col.down; row != col; row = row.down) {
            solution[k] = row;
            STEPS++;
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

    // Selects the column best fit for the move
    private ColumnNode selectColumn() {
        int min = Integer.MAX_VALUE;
        ColumnNode best = null;
        for (ColumnNode c = (ColumnNode) header.right; c != header; c = (ColumnNode) c.right) {
            if (c.size < min) {
                min = c.size;
                best = c;
            }
        }
        return best;
    }

    // Covers the column and row when we decide to make a move
    private void cover(ColumnNode col) {
        col.right.left = col.left;
        col.left.right = col.right;
        for (Node row = col.down; row != col; row = row.down) {
            for (Node j = row.right; j != row; j = j.right) {
                j.down.up = j.up;
                j.up.down = j.down;
                j.column.size--;
            }
        }
    }

    // Uncovers a column and rows if move leads to dead end
    private void uncover(ColumnNode col) {
        for (Node row = col.up; row != col; row = row.up) {
            for (Node j = row.left; j != row; j = j.left) {
                j.column.size++;
                j.down.up = j;
                j.up.down = j;
            }
        }
        col.right.left = col;
        col.left.right = col;
    }

    // Returns a copy of the board
    private int[][] hardCopy(int[][] original) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++)
            System.arraycopy(original[i], 0, copy[i], 0, 9);
        return copy;    
    }

 }
