// SudokuDlx.java
import java.util.ArrayList;
import java.util.List;

public class SudokuDlx implements SudokuSolver {
    private static final int N = 9;
    private ColumnNode header;
    private List<ColumnNode> columnList;
    private List<DLXNode> solution;

    @Override
    public SudokuBoard solve(SudokuBoard puzzle) {
        buildDLX(puzzle);
        solution = new ArrayList<>();
        search(0);
        return solutionToBoard();
    }

    private void buildDLX(SudokuBoard puzzle) {
        // 324 constraints = 9*9 cells + 9*9 row-num + 9*9 col-num + 9*9 block-num
        header = new ColumnNode("header");
        columnList = new ArrayList<>(324);
        // create column headers
        ColumnNode prev = header;
        for (int i = 0; i < 324; i++) {
            ColumnNode col = new ColumnNode(Integer.toString(i));
            columnList.add(col);
            // link horizontally
            prev.right = col;  col.left = prev;
            prev = col;
        }
        prev.right = header;
        header.left = prev;

        // for each cell r,c
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int given = puzzle.get(r, c);
                // try all n=1..9 or only the given one
                for (int n = 1; n <= N; n++) {
                    if (given != 0 && n != given) continue;
                    // list of 4 constraint indices
                    int block = (r / 3) * 3 + (c / 3);
                    int[] cols = new int[] {
                        r * 9 + c,              // cell
                        81 + r * 9 + (n - 1),   // row-number
                        162 + c * 9 + (n - 1),  // col-number
                        243 + block * 9 + (n - 1) // block-number
                    };
                    addDataRow(r, c, n, cols);
                }
            }
        }
    }

    private void addDataRow(int row, int col, int num, int[] cols) {
        DLXNode first = null;
        for (int ci : cols) {
            ColumnNode column = columnList.get(ci);
            DLXNode newNode = new DLXNode(column, row, col, num);
            // link into column (vertical)
            newNode.down = column;
            newNode.up = column.up;
            column.up.down = newNode;
            column.up = newNode;
            column.size++;

            // link row (horizontal)
            if (first == null) {
                first = newNode;
                first.left = first;
                first.right = first;
            } else {
                newNode.right = first;
                newNode.left = first.left;
                first.left.right = newNode;
                first.left = newNode;
            }
        }
    }

    private void cover(ColumnNode col) {
        col.right.left = col.left;
        col.left.right = col.right;
        for (DLXNode row = col.down; row != col; row = row.down) {
            for (DLXNode node = row.right; node != row; node = node.right) {
                node.down.up = node.up;
                node.up.down = node.down;
                node.column.size--;
            }
        }
    }

    private void uncover(ColumnNode col) {
        for (DLXNode row = col.up; row != col; row = row.up) {
            for (DLXNode node = row.left; node != row; node = node.left) {
                node.column.size++;
                node.down.up = node;
                node.up.down = node;
            }
        }
        col.right.left = col;
        col.left.right = col;
    }

    private boolean search(int k) {
        if (header.right == header) {
            return true;  // found
        }
        // choose smallest-size column
        ColumnNode col = null;
        int minSize = Integer.MAX_VALUE;
        for (ColumnNode j = (ColumnNode) header.right; j != header; j = (ColumnNode) j.right) {
            if (j.size < minSize) {
                minSize = j.size;
                col = j;
            }
        }
        cover(col);
        for (DLXNode row = col.down; row != col; row = row.down) {
            solution.add(row);
            for (DLXNode node = row.right; node != row; node = node.right) {
                cover(node.column);
            }
            if (search(k + 1)) return true;
            // backtrack
            solution.remove(solution.size() - 1);
            for (DLXNode node = row.left; node != row; node = node.left) {
                uncover(node.column);
            }
        }
        uncover(col);
        return false;
    }

    private SudokuBoard solutionToBoard() {
        SudokuBoard out = new SudokuBoard();
        for (DLXNode n : solution) {
            out.set(n.row, n.col, n.num);
        }
        return out;
    }

    // ---- DLX data structures ----
    private static class DLXNode {
        DLXNode left, right, up, down;
        ColumnNode column;
        int row, col, num;
        DLXNode(ColumnNode colNode, int r, int c, int n) {
            this.column = colNode;
            this.up = colNode.up;
            this.down = colNode;
            this.row = r;
            this.col = c;
            this.num = n;
        }
    }

    private static class ColumnNode extends DLXNode {
        int size;
        String name;
        ColumnNode left, right;
        ColumnNode(String name) {
            super(null, -1, -1, -1);
            this.name = name;
            this.size = 0;
            this.up = this;
            this.down = this;
            this.column = this;
        }
    }
}
