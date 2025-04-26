import java.util.ArrayList;
import java.util.List;

public class HardcodedPuzzles {
    /** Each puzzle is given as an 81-char string, row by row;
     * use '.' or '0' for blanks.
     */
    private static final String[] PUZZLE_STRINGS = {
        // Puzzle 1
        "530070000" +
        "600195000" +
        "098000060" +
        "800060003" +
        "400803001" +
        "700020006" +
        "060000280" +
        "000419005" +
        "000080079",

        // Puzzle 2
        "100007090" +
        "030020008" +
        "009600500" +
        "005300900" +
        "010080002" +
        "600004000" +
        "059000073" +
        "002010080" +
        "070000100"
    };

    /** Returns a list of SudokuBoard instances for each hard-coded puzzle. */
    public static List<SudokuBoard> getPuzzles() {
        List<SudokuBoard> list = new ArrayList<>();
        for (String s : PUZZLE_STRINGS) {
            list.add(SudokuBoard.fromString(s));
        }
        return list;
    }
}
